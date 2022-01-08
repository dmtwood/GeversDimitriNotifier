package be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders

import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.users_in_notify_list.view.*

class ContactItem(val contact: User): Item<ViewHolder>() {

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