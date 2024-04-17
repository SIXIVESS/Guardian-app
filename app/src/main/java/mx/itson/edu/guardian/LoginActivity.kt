package mx.itson.edu.guardian

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button : Button = findViewById(R.id.loginButton)
        val buttonDos : Button = findViewById(R.id.clickHere)

        button.setOnClickListener {
            var intent: Intent = Intent( this, MenuActivity::class.java)
            startActivity(intent)
        }

        buttonDos.setOnClickListener {
            var intent: Intent = Intent( this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}