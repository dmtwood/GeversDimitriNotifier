package be.dimitrigevers.android.geversdimitrinotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        loginVerification()
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
        menuInflater.inflate(R.menu.nav_bar ,menu)
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