package be.dimitrigevers.android.geversdimitrinotifier.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import be.dimitrigevers.android.geversdimitrinotifier.auth.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationsActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        fetchUser()
        loginVerification()
    }

    private fun fetchUser() {
        Log.d(MessageLogActivity.MESSAGE_TAG, "in fetchUser()")

        val uid = FirebaseAuth.getInstance().uid
        val dbRefUsers = FirebaseDatabase.getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/").getReference("/users/$uid")

        dbRefUsers.addListenerForSingleValueEvent( object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)
                Log.d(MessageLogActivity.MESSAGE_TAG, "in on data change")
                Log.d(MessageLogActivity.MESSAGE_TAG, "current user in fetchUser(): ${currentUser?.userName}")
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    // VERIFY IF USER IS LOGGED IN
    private fun loginVerification() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    // CREATE MENU-ITEMS
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // MAP MENU-ITEMS NEW & SIGN-OUT TO INTENTS
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navbar_new_message -> {
                val notifyIntent = Intent(this, NotifyActivity::class.java)
                startActivity(notifyIntent)
            }
            R.id.navbar_sign_out -> {
                val registerIntent = Intent(this, RegisterActivity::class.java)
                registerIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(registerIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}