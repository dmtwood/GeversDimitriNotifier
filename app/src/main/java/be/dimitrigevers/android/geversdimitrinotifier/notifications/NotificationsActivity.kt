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
import be.dimitrigevers.android.geversdimitrinotifier.models.Notification
import be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders.NotificationsLine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_notifications.*

class NotificationsActivity : AppCompatActivity() {
    companion object {
        var currentUser: User? = null
    }
    private val notificationsAdapter = GroupAdapter<ViewHolder>()
    val currentNotifications = HashMap<String, Notification>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        fetchUser()
        loginVerification()
        fetchNotifications()
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


    private fun fetchUser() {
        val uid = FirebaseAuth.getInstance().uid
        val dbRefUsers = FirebaseDatabase
            .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("/users/$uid")

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


    private fun fetchNotifications() {
        val uidFrom = FirebaseAuth.getInstance().uid
        val dbRefLatestNotification = FirebaseDatabase
            .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("/latest-notifications/$uidFrom")

        dbRefLatestNotification.addChildEventListener( object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val notification = snapshot.getValue(Notification::class.java) ?: return
                Log.d("NOTIFICATIONSLOGGER", "snapshot message for hashmap: ${notification.message}")


                // currentNotifications["test"] = Notification("1", "test", "test", "test", 100L)
                currentNotifications[snapshot.key!!] = notification //= notification
                // currentNotifications[snapshot.toString()] = notification
                // currentNotifications[snapshot.key] = notification
                // notificationsAdapter.add(NotificationsLine(notification))
                Log.d("NOTIFICATIONSLOGGER", "full hashmap hashmap: ${currentNotifications.keys}")
                Log.d("NOTIFICATIONSLOGGER", "line in on child added to hashmap: ${currentNotifications["test"]}")
                refreshNotifications()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val notification = snapshot.getValue(Notification::class.java) ?: return
                currentNotifications[snapshot?.key.toString()] = notification
                Log.d("NOTIFICATIONSLOGGER", "line in on child changed to hashmap: ${currentNotifications[snapshot.key!!]}")

                refreshNotifications()
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
        notifications_recyclerview.adapter = notificationsAdapter
    }


    private fun refreshNotifications() {
        notificationsAdapter.clear()
        currentNotifications.values.forEach{
            notificationsAdapter.add(NotificationsLine(it))
            if (it != null) {
                Log.d("NOTIFICATIONSLOGGER", "line in refresh notifications: ${it.message}")
            }
        }
    }
}

