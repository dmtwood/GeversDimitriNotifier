package be.dimitrigevers.android.geversdimitrinotifier

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register.*

class UserAdapter (private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_in_notify_list, parent,false)
        return  UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        var user = userList[position]
        holder.userName.text = user.userName
        // holder.userImage = ((CircleImageView) user.user_img_uri)

        // Picasso. .with(this).get(user.user_img_uri)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var userName : TextView = itemView.findViewById(R.id.user_in_notify_list_name)
        var userImage : CircleImageView = itemView.findViewById(R.id.user_in_notify_list_image)
    }
}