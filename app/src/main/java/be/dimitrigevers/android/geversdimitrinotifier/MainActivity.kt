package be.dimitrigevers.android.geversdimitrinotifier

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val registerButton = findViewById<Button>(R.id.register_button_registerform)
        registerButton.setOnClickListener {

            // via synthetic view, not recommended anymore
            val username = username_edittext_registerform.text.toString()
            val email = findViewById<EditText>(R.id.email_input_registerform)
            val emailstring = email.getText().toString()
            val password = findViewById<EditText>(R.id.password_input_registerform)
            val password_string = password.getText().toString()

            Log.d("MainActivity", "Username: " + username)
            Log.d("MainActivity", "Email: " + emailstring)
            Log.d("MainActivity", "Password: " + password_string)
        }

        login_link_registerform.setOnClickListener {
            // launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

    }
}

// XML sources
// Rounded input fields on activity_main.xml & activity_login.xml
// https://stackoverflow.com/questions/3646415/how-to-create-edittext-with-rounded-corners


