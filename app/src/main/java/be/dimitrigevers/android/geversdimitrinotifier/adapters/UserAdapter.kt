package be.dimitrigevers.android.geversdimitrinotifier.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.dimitrigevers.android.geversdimitrinotifier.R
import be.dimitrigevers.android.geversdimitrinotifier.User
import be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders.UserViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (private val userList: ArrayList<User>): RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_in_notify_list, parent,false)
        return  UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = userList[position]
        holder.userName.text = user.userName
        // holder.userImage = ((CircleImageView) user.user_img_uri)
        // Picasso. .with(this).get(user.user_img_uri)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}