package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.itson.edu.guardian.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //setContentView(R.layout.activity_login)

        //AUTENTIFICACIÓN
        binding.loginButton.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "ERROR: Datos incorrectos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.loginButton.isEnabled = false
                Login(email, password)
            }

        }

        //REGISTRO
        val btnSignUp: Button = findViewById(R.id.clickHere)
        btnSignUp.setOnClickListener {
            var intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

        private fun Login(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.loginButton.isEnabled = true
                    if (task.isSuccessful) {
                        Log.d("TAG", "¡Buena Suerte!")
                        Toast.makeText(baseContext, "¡Buena Suerte!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MenuActivity::class.java)
                        intent.putExtra("correo", email)

                        startActivity(intent)
                    } else {
                        Log.w("TAG", "No se pudo iniciar sesión", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

}
