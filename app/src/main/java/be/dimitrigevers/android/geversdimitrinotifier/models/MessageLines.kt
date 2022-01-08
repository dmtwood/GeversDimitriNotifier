package be.dimitrigevers.android.geversdimitrinotifier.models

import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.message_incoming_row.view.*
import kotlinx.android.synthetic.main.message_outgoing_row.view.*


class MessageFromLine(val messageFrom: String, val currentUser: User): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.message_from.text = messageFrom
        val currentUserImageUri = currentUser.user_img_uri
        val imageViewForPicasso = viewHolder.itemView.image_from
        Picasso.get().load(currentUserImageUri).into(imageViewForPicasso)
    }

    override fun getLayout(): Int {
        return R.layout.message_incoming_row
    }
}

class MessageToLine(val messageTo: String, val contact: User): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.message_to.text = messageTo
        val contactImageUri = contact.user_img_uri
        val imageViewToPicasso = viewHolder.itemView.image_to
        Picasso.get().load(contactImageUri).into(imageViewToPicasso)
    }

    override fun getLayout(): Int {
        return R.layout.message_outgoing_row
    }
}