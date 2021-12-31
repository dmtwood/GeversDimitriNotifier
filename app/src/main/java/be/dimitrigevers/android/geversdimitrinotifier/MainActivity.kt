package be.dimitrigevers.android.geversdimitrinotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val registerButton = findViewById<Button>(R.id.register_button_registerform)
        registerButton.setOnClickListener {

            /* --- RETRIEVE USER CREDENTIALS FROM UI --- */
            // via synthetic view, not recommended anymore
            val username = username_edittext_registerform.text.toString()
            val email = findViewById<EditText>(R.id.email_input_registerform)
            val email_input = email.getText().toString()
            val password = findViewById<EditText>(R.id.password_input_registerform)
            val password_input = password.getText().toString()

            // TESTING PURPOSE - DELETE IN PRODUCTION ENVIRONMENT
            Log.d("MainActivity", "Username: " + username)
            Log.d("MainActivity", "Email: " + email_input)
            Log.d("MainActivity", "Password: " + password_input)

            /* --- CREATE FIREBASE AUTHENTICATION --- */
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_input, password_input)
                .addOnCompleteListener {
                    if (!it.isSuccessful)
                        return@addOnCompleteListener
                    Log.d("Main", "User created with uid: ${it.result?.user?.uid}")
                }
        }

        login_link_registerform.setOnClickListener {
            // launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

/*    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }
 */
}



// XML sources
// Rounded input fields on activity_main.xml & activity_login.xml
// https://stackoverflow.com/questions/3646415/how-to-create-edittext-with-rounded-corners


