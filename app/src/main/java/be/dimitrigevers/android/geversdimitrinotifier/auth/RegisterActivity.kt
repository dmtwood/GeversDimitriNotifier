package be.dimitrigevers.android.geversdimitrinotifier.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import be.dimitrigevers.android.geversdimitrinotifier.notifications.NotificationsActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.FileDescriptor
import java.io.IOException
import java.util.*


class RegisterActivity : AppCompatActivity() {
    val LOG_TAG = "LogRegisterActivity: "
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        val registerButton = findViewById<Button>(R.id.register_button_registerform)
        registerButton.setOnClickListener {
            register()
        }
        login_link_registerform.setOnClickListener {
            // launch login activity
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        image_upload_button_registerform.setOnClickListener {
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, 0)
        }
    }

    var selectedImageUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == 0 && resultCode == Activity.RESULT_OK && data != null ) {
            Log.d(LOG_TAG, "image upload selector ")
            selectedImageUri = data.data
            if (selectedImageUri == null) {
                Log.d(LOG_TAG, "selected img is null")
                return
            }
            val imageForUpload = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            // val imageForUpload = uriToBitmap(selectedImageUri!!)

            // to do - show image in ui
            image_for_upload_registerform.setImageBitmap(imageForUpload)
//            image_for_upload_registerform.alpha = 0f
        }
    }


    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    private fun register() {

        /* --- RETRIEVE USER CREDENTIALS FROM UI --- */
        // via synthetic view, not recommended anymore
        val username = username_edittext_registerform.text.toString()
        val email = findViewById<EditText>(R.id.email_input_registerform)
        val email_input = email.getText().toString()
        val password = findViewById<EditText>(R.id.password_input_registerform)
        val password_input = password.getText().toString()
        if (email_input.isEmpty() || password_input.isEmpty()) {
            Toast
                .makeText(this, "Email & password (6+ chars) required", Toast.LENGTH_SHORT)
                .show()
            return
        }
        Log.d(LOG_TAG, "Username: " + username)
        Log.d(LOG_TAG, "Email: " + email_input)
        Log.d(LOG_TAG, "Password: " + password_input)

        /* --- CREATE FIREBASE AUTHENTICATION --- */
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_input, password_input)
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                Log.d("Main", "User created with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "Created succesfully", Toast.LENGTH_SHORT).show()
                /* --- create second thread for uploading the image to firebase  ---*/
                runBlocking {
                    launch { imageToFireBaseDB() }
                }
            }
            .addOnFailureListener{
            Log.d("Main", "Creation failed")
            Toast.makeText(this, "Creation failed", Toast.LENGTH_SHORT).show()
            }
    }

    private suspend fun imageToFireBaseDB() {
        val storageRef = FirebaseStorage.getInstance().reference
        if (selectedImageUri == null) {
            Log.d(LOG_TAG, "selected img is null")
            return
        }
        val imageName = UUID.randomUUID().toString()
        val imageRef = FirebaseStorage.getInstance().getReference("/images/$imageName")
        imageRef.putFile( selectedImageUri!! )
            .addOnSuccessListener {
                Log.d(LOG_TAG, "Image upload was succesfull: ${it.metadata?.path}")

                imageRef.downloadUrl.addOnSuccessListener {
                    Log.d(LOG_TAG, "Image upload succesfull at $it")
                    userToFirebaseDB(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(LOG_TAG, "Failed to set value to database: ${it.message}")
            }

        Log.d(LOG_TAG, "image ref : $imageRef - sel image uri: $selectedImageUri")
        // storageRef.putFile(selectedImageUri!!)
    }

    private fun userToFirebaseDB(userImgUri:String) {
        Log.d(LOG_TAG, "trying to store user to db with img uri ${userImgUri.toString()}" )
        val uid = FirebaseAuth.getInstance().uid?: ""
        val db = FirebaseDatabase.getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app")
        val dbReference = db.getReference("/users/$uid")

        val username = findViewById<EditText>(R.id.username_edittext_registerform).text.toString()
        val userToStore = User(uid, username, userImgUri.toString())

        dbReference.setValue(userToStore)
            .addOnSuccessListener {
                Log.d(LOG_TAG, "User saved successfully in Firestore")
                val notificationsIntent = Intent(this, NotificationsActivity::class.java)
                // clear out stack
                notificationsIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                    /* AFTER SUCCESSFUL USER CREATION, GO TO NOTIFICATIONS PAGE */
                startActivity(notificationsIntent)
            }
            .addOnFailureListener {
                Log.d(LOG_TAG, "Failed to save user to database")
            }
        Log.d(LOG_TAG, "passed setValue to store user: ${userToStore.userName}")

    }

}



// XML sources
// Rounded input fields in activity_register.xml & activity_login.xml
// https://stackoverflow.com/questions/3646415/how-to-create-edittext-with-rounded-corners

// Format images in round shape in activity_register.xml
// https://github.com/hdodenhof/CircleImageView

// Groupie is a simple, flexible library for complex RecyclerView layout
// https://github.com/lisawray/groupie