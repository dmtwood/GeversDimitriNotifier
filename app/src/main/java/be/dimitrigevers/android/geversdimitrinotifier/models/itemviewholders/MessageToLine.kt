package be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders

import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.message_outgoing_row.view.*


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