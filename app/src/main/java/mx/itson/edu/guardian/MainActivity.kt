package mx.itson.edu.guardian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.button)

        button.setOnClickListener {
            var intent: Intent = Intent( this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}