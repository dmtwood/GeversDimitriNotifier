package be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.dimitrigevers.android.geversdimitrinotifier.R
import de.hdodenhof.circleimageview.CircleImageView

public class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var userName : TextView = itemView.findViewById(R.id.user_in_notify_list_name)
    var userImage : CircleImageView = itemView.findViewById(R.id.user_in_notify_list_image)
}