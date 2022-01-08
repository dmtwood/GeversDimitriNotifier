package be.dimitrigevers.android.geversdimitrinotifier.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_message_log.*

class MessageLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_log)


         val user = intent.getParcelableExtra<User>(NotifyActivity.CONTACT_KEY)
        if (user != null) {
            supportActionBar?.title = user.userName
        }

        populateMessageLog()


    }
    private fun populateMessageLog() {
        val contactAdapter = GroupAdapter<ViewHolder>()

        contactAdapter.add(MessageFromLine())
        contactAdapter.add(MessageToLine())
        contactAdapter.add(MessageFromLine())

        message_log_recyclerview.adapter = contactAdapter
    }
}


class MessageFromLine: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.message_incoming_row
    }
}

class MessageToLine: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.message_outgoing_row
    }
}