package be.dimitrigevers.android.geversdimitrinotifier.models.itemviewholders

import be.dimitrigevers.android.geversdimitrinotifier.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.notifications_row.view.*

class NotificationsLine(val notification: be.dimitrigevers.android.geversdimitrinotifier.models.Notification?) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.notifications_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (notification != null) {
            viewHolder.itemView.notifications_row_message.text = notification.message
        }
    }
}
