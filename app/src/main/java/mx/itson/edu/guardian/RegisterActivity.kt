package mx.itson.edu.guardian

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AlertDialog


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val button : Button = findViewById(R.id.registerButton)
        val buttonDos : Button = findViewById(R.id.clickHereComp)

        button.setOnClickListener {
            var intent: Intent = Intent( this, LoginActivity::class.java)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cuenta creada")
            builder.setMessage("Tu cuenta ha sido creada. Por favor, inicia sesión.")
            builder.show()
            startActivity(intent)
        }

            buttonDos.setOnClickListener {
            var intent: Intent = Intent( this, RegisterCompActivity::class.java)
            startActivity(intent)


        }


    }
}