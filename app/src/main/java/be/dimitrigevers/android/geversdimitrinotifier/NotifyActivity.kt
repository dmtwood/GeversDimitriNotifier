package be.dimitrigevers.android.geversdimitrinotifier

import android.icu.number.NumberFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

import kotlinx.android.synthetic.main.activity_notify.*

class NotifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)

        supportActionBar?.title = "Choose contact"


        // Picasso.with(this).load(url).into(imageView);
        val userAdapter = GroupAdapter<ViewHolder>()
        // 'old method'
        notify_recyclerview.adapter
        notify_recyclerview.layoutManager = LinearLayoutManager(this)

        fetchUsers()
    }

    private fun fetchUsers() {
        val dbRef = FirebaseDatabase.getInstance().getReference("/users")
        dbRef.addListenerForSingleValueEvent(object:ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    Log.d("ADAPTER", it.toString())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

class ContactItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getLayout(): Int {
        TODO("Not yet implemented")
    }

}
