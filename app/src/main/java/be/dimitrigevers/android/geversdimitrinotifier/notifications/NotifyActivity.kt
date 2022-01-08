package be.dimitrigevers.android.geversdimitrinotifier.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders.ContactItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

import kotlinx.android.synthetic.main.activity_notify.*
import kotlinx.android.synthetic.main.users_in_notify_list.view.*

class NotifyActivity : AppCompatActivity() {
    companion object {
        val CONTACT_KEY = "CONTACT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        supportActionBar?.title = "Choose contact"
        fetchContacts()
    }

    private fun fetchContacts() {
        val dbRef = FirebaseDatabase
            .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("/users")

        // values from Firebase Database in Kotlin? https://stackoom.com/en/question/4PIJp
        dbRef.addListenerForSingleValueEvent(object:ValueEventListener {

            // USE RECYCLERVIEW WITH CONTACTS ADAPTER TO POPULATE THE CONTACT LIST
            override fun onDataChange(p0: DataSnapshot) {
                val contactsAdapter = GroupAdapter<ViewHolder>()
                p0.children.forEach{
                    val contact = it.getValue(User::class.java)
                    if (contact != null) {
                        contactsAdapter.add(ContactItem(contact))
                    }
                    Log.d("Notify contact list: ", it.toString())
                }
                contactsAdapter.setOnItemClickListener { item, myParentView ->
                    val contactItem = item as ContactItem
                    val messageLogIntent = Intent(myParentView.context, MessageLogActivity::class.java)
                    // needs a parcelable object,
                    // annotate the model class and in buid.gradle set androidExtensions { experimental = true }
                    messageLogIntent.putExtra(CONTACT_KEY, contactItem.contact)
                    startActivity(messageLogIntent)
                    finish()
                }
                notify_recyclerview.adapter = contactsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}


