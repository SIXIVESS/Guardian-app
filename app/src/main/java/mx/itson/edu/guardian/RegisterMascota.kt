package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterMascota : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var datos: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        datos = intent.getStringExtra("correo") ?: ""

        val btnGuardar: Button = findViewById(R.id.registerBtn)
        val btnCancelar:Button = findViewById(R.id.cancelBtn)

        btnGuardar.setOnClickListener {
            registroMascota()
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this, MascotaActivity::class.java)
            intent.putExtra("correo", datos)
            startActivity(intent)
        }
    }

    private fun registroMascota() {
        val txtNombre: EditText = findViewById(R.id.nombreMascota)
        val txtRaza: EditText = findViewById(R.id.razaMascota)
        val txtDescripcion: EditText = findViewById(R.id.descripcionMascota)
        val txtEdad: EditText = findViewById(R.id.edadMascota)

        val nombre = txtNombre.text.toString()
        val raza = txtRaza.text.toString()
        val descripcion = txtDescripcion.text.toString()
        val edad = txtEdad.text.toString().toIntOrNull()

        if (edad == null) {
            Toast.makeText(this, "Ingrese una edad válida", Toast.LENGTH_SHORT).show()
            return
        }
        val userRef = database.getReference("Users").orderByChild("correo").equalTo(datos)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val usuarioSnapshot = dataSnapshot.children.firstOrNull()
                    val idUsuario = usuarioSnapshot?.key

                    if (idUsuario != null) {
                        val referenciaMasc = database.getReference("Mascotas").push()
                        val nuevaMascota = Pet(nombre,raza,descripcion,edad, idUsuario)
                        referenciaMasc.setValue(nuevaMascota)
                            .addOnSuccessListener {
                                Toast.makeText(this@RegisterMascota, "Mascota registrada", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.e("RegistrarMascota", "Error: ${e.message}")
                                Toast.makeText(this@RegisterMascota, "Error", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Log.e("RegistrarMascota", "No se encontró el correo proporcionado")
                        Toast.makeText(this@RegisterMascota, "Error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("RegistrarMascota", "No se encontró el correo proporcionado")
                    Toast.makeText(this@RegisterMascota, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RegistrarMascota", "Error en la base de datos: ${error.message}")
                Toast.makeText(this@RegisterMascota, "Error en la base de datos: ", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
