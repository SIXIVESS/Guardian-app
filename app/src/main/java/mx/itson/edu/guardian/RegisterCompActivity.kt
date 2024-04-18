package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterCompActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_empresa)

        val button : Button = findViewById(R.id.registerCompButton)
        val buttonDos : Button = findViewById(R.id.clickHereClient)

        button.setOnClickListener {
            var intent: Intent = Intent( this, LoginActivity::class.java)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cuenta creada")
            builder.setMessage("Tu cuenta ha sido creada. Por favor, inicia sesión.")
            builder.show()
            startActivity(intent)
        }

        buttonDos.setOnClickListener {
            var intent: Intent = Intent( this, RegisterActivity::class.java)
            startActivity(intent)


        }


    }
}