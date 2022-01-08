package be.dimitrigevers.android.geversdimitrinotifier.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import be.dimitrigevers.android.geversdimitrinotifier.models.MessageFromLine
import be.dimitrigevers.android.geversdimitrinotifier.models.MessageToLine
import be.dimitrigevers.android.geversdimitrinotifier.models.Notification
import be.dimitrigevers.android.geversdimitrinotifier.notifications.NotificationsActivity.Companion.currentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_message_log.*
import kotlinx.android.synthetic.main.message_incoming_row.view.*
import kotlinx.android.synthetic.main.message_outgoing_row.view.*

class MessageLogActivity : AppCompatActivity() {

    companion object { val MESSAGE_TAG = "MessageLog" }

    val messageLogAdapter = GroupAdapter<ViewHolder>()

    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(MESSAGE_TAG, "on creation, current user: ${currentUser?.userName}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_log)

        message_log_recyclerview.adapter = messageLogAdapter

         toUser = intent.getParcelableExtra<User>(NotifyActivity.CONTACT_KEY)
        if (toUser != null) {
            supportActionBar?.title = toUser!!.userName
        }

        listenForNotifications()

        // send message when send button is clicked
        message_log_send_button.setOnClickListener {
            Log.d(MESSAGE_TAG, "click send message button")
            sendMessage()
        }

    }


    private fun sendMessage() {
        val dbRefToNotifications = FirebaseDatabase
            .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("/notifications")
            .push() // to render the id after creation

        val fromId = FirebaseAuth.getInstance().uid
        val toUser = intent.getParcelableExtra<User>(NotifyActivity.CONTACT_KEY)
        val toId = toUser?.uid
        val typedMessage = message_log_input_field.text.toString()

        if (fromId == null || toId == null ) return

        val notification = Notification(dbRefToNotifications.key!!,typedMessage, fromId, toId, System.currentTimeMillis())

        dbRefToNotifications.setValue(notification)
            .addOnSuccessListener {
                Log.d(MESSAGE_TAG, "Notification saved in firestore with id: ${dbRefToNotifications.key} ")
            }

    }

    private fun listenForNotifications() {

        val dbRefToNotifications = FirebaseDatabase
            .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("/notifications")

        // check for changes in any child of the notifications node in Firestore and auto refresh
        // https://stackoverflow.com/questions/40764292/what-is-the-difference-between-childeventlistener-and-valueeventlistener-firebas
        dbRefToNotifications.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(MESSAGE_TAG, "to User in child added: ${toUser!!.userName}")


                val notification = snapshot.getValue(Notification::class.java)

                if (notification != null) {
                    Log.d(MESSAGE_TAG, notification.message.toString() )

                    Log.d(MESSAGE_TAG, "from User: ${notification.fromId}")
                    Log.d(MESSAGE_TAG, "logged User: ${FirebaseAuth.getInstance().uid}")

                    // check if the message is from the logged in user
                    if (notification.fromId.toString() == FirebaseAuth.getInstance().uid.toString()) {
                        Log.d(MESSAGE_TAG, "current == logged")
                        val currentUser = NotificationsActivity.currentUser ?: return
                        //val currentUser = FirebaseAuth.getInstance().currentUser.
                        Log.d(MESSAGE_TAG, "current User: ${currentUser?.userName}")

                        messageLogAdapter.add(MessageFromLine(notification.message, currentUser))
                    } else {
                        Log.d(MESSAGE_TAG, "current =! logged")
                        Log.d(MESSAGE_TAG, "to User: ${toUser!!.userName} and current logged id : ${FirebaseAuth.getInstance().uid}")
                        messageLogAdapter.add(MessageToLine(notification.message, toUser!!))
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

