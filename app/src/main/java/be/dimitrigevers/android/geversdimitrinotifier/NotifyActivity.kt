package be.dimitrigevers.android.geversdimitrinotifier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        supportActionBar?.title = "Choose contact"
        fetchContacts()
    }
    private fun fetchContacts() {
        Log.d("jefke", "in fetchContacts()")
        val dbRef = FirebaseDatabase
            .getInstance("https://geversdimitrinotifier-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("/users")
        Log.d("jefke", dbRef.toString())
        // values from Firebase Database in Kotlin? https://stackoom.com/en/question/4PIJp
        dbRef.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val contactsAdapter = GroupAdapter<ViewHolder>()
                p0.children.forEach{
                    val contact = it.getValue(User::class.java)
                    if (contact != null) {
                        contactsAdapter.add(ContactItem(contact))
                    }
                    Log.d("jefke", it.toString())
                }
                notify_recyclerview.adapter = contactsAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

class ContactItem(private val contact: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        // run for each contact in list
        viewHolder.itemView.user_in_notify_list_name.text = contact.userName
        // https://www.tutorialspoint.com/how-do-i-load-an-image-by-url-using-picasso-library-on-kotlin
        Picasso.get().load(contact.user_img_uri).into(viewHolder.itemView.user_in_notify_list_image)
    }

    override fun getLayout(): Int {
        return R.layout.users_in_notify_list
    }

}
