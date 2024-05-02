package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import mx.itson.edu.guardian.databinding.ActivityRegistroBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val password: EditText = findViewById<EditText>(R.id.txtPassword) as EditText
        val repPassword: EditText = findViewById<EditText>(R.id.txtRepPass) as EditText

        /*
        setContentView(R.layout.activity_registro)

        val button : Button = findViewById(R.id.registerButton)
        val buttonDos : Button = findViewById(R.id.clickHereComp)
*/

        binding.registerButton.setOnClickListener {
            val txnombre = binding.txtName.text.toString()
            val txemail = binding.txtEmail.text.toString()
            val txpassword = binding.txtPassword.text.toString()
            val txrepPassword = binding.txtRepPass.text.toString()

            if (txpassword == txrepPassword) {
                Register(txemail, txpassword)
                saveMarkFromForm()
            } else {
                Toast.makeText(baseContext, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun Register(email: String, password: String) {
        if (password.length < 8) {
            Toast.makeText(baseContext, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Registro exitoso")
                    inicio()
                } else {
                    Log.w("TAG", "Error al registrarse", task.exception)
                    Toast.makeText(baseContext, "Fallo: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun inicio() {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun saveMarkFromForm() {
        val nombre: EditText = findViewById<EditText>(R.id.txtName) as EditText
        val correo: EditText = findViewById<EditText>(R.id.txtEmail) as EditText
        val contra: EditText = findViewById<EditText>(R.id.txtPassword) as EditText
        val contraDos = contra.text.toString()

        if (contraDos.length < 6) {
            Toast.makeText(baseContext, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        val usuario = User(
            nombre.text.toString(),
            correo.text.toString(),
            contra.text.toString()
        )
        userRef.push().setValue(usuario)
    }
    }
