package be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders

import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.notifications_row.view.*

class NotificationsLine(val notification: be.dimitrigevers.android.geversdimitrinotifier.models.Notification?) : Item<ViewHolder>() {

    var contact: User? = null

    override fun getLayout(): Int {
        return R.layout.notifications_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (notification != null) {
            viewHolder.itemView.notifications_row_message.text = notification.message

            val toId: String
            if ( FirebaseAuth.getInstance().uid == notification.fromId ) {
                toId = notification.toId
            } else {
                toId = notification.fromId
            }

            val dbRefUserTo = FirebaseDatabase
                .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("/users/$toId")

            dbRefUserTo.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    contact = snapshot.getValue(User::class.java)
                    if (contact != null) {
                        viewHolder.itemView.notifications_row_contact.text = contact!!.userName
                        val notificationsView = viewHolder.itemView.notifications_row_image
                        Picasso.get().load(contact!!.user_img_uri).into(notificationsView)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }
}
