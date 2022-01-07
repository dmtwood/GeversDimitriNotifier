package be.dimitrigevers.android.geversdimitrinotifier.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.dimitrigevers.android.geversdimitrinotifier.R

class MessageLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_log)

        supportActionBar?.title = "Message log"
    }
}