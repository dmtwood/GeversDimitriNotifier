package be.dimitrigevers.android.geversdimitrinotifier

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_loginform.setOnClickListener {

            val email = findViewById<EditText>(R.id.email_input_loginform)
            val emailstring = email.getText().toString()
            val password = findViewById<EditText>(R.id.password_input_loginform)
            val password_string = password.getText().toString()

            Log.d("MainActivity", "Email: " + emailstring)
            Log.d("MainActivity", "Password: " + password_string)
        }

        back_to_main_link_loginform.setOnClickListener {
            finish()
        }



    }
}