package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class InfoMascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        val intent = intent
        val nombre = intent.getStringExtra("nombre")
        val raza = intent.getStringExtra("raza")
        val descripcion = intent.getStringExtra("descripcion")
        val edad = intent.getIntExtra("edad", 0).toString()

        val idMascota = intent.getStringExtra("idMascota")
        val idUser = intent.getStringExtra("idDuenio")
        val datos = intent.getStringExtra("correo")

        val txtNombre: EditText = findViewById(R.id.nombreMascota)
        val txtRaza: EditText = findViewById(R.id.razaMascota)
        val txtEdad: EditText = findViewById(R.id.edadMascota)
        val txtDescripcion: EditText = findViewById(R.id.descripcionMascota)

        txtNombre.setText(nombre)
        txtRaza.setText(raza)
        txtDescripcion.setText(descripcion)
        txtEdad.setText(edad)

        val agendaButton : Button = findViewById(R.id.calendarButton)
        val editButton : Button = findViewById(R.id.editButton)
        val deleteButton : Button = findViewById(R.id.eraseButton)

        agendaButton.setOnClickListener {
            val intent = Intent(this, ReservacionesActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }

        editButton.setOnClickListener {
            val idMascota = intent.getStringExtra("idMascota") ?: return@setOnClickListener

            val refMas = FirebaseDatabase.getInstance().getReference("Mascotas").child(idMascota)

            // Actualizar los datos de la mascota
            val nuevaMascota = Pet(
                txtNombre.toString(),
                txtRaza.text.toString(),
                txtDescripcion.text.toString(),
                txtEdad.text.toString().toInt(),

                idUser
            )
            refMas.updateChildren(nuevaMascota.toMap())
                .addOnSuccessListener {
                    Toast.makeText(this, "Mascota actualizada ", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("Mascota", "Error: ${e.message}")
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
        }

        deleteButton.setOnClickListener {
            val idMascota = intent.getStringExtra("idMascota") ?: return@setOnClickListener

            val mascotaRef = FirebaseDatabase.getInstance().getReference("Mascotas").child(idMascota)

            mascotaRef.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Mascota eliminada ", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("Mascota", "Error: ${e.message}")
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
        }

    }


}