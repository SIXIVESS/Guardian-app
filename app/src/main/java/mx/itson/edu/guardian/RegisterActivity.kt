package mx.itson.edu.guardian

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

import android.os.Bundle
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Set up the "Registrarse" button
        val registerButton = findViewById<Button>(R.id.button3)
        registerButton.setOnClickListener {
            // Start the MenuActivity when the button is clicked
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
}