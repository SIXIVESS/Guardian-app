package mx.itson.edu.guardian

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class MascotaActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMascotaBinding
    private lateinit var database: FirebaseDatabase
    //private lateinit var petName1: TextView
    //private lateinit var petName2: TextView
    //private lateinit var petRef: DatabaseReference

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota)

        // petName1 = findViewById(R.id.nombreMascota)
        // petName2 = findViewById(R.id.nombreMascota1)

        /*
        super.onCreate(savedInstanceState)
        binding= ActivityMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------NAV----------------------------
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpTabBar()
*/
        //-------------------CRUD----------------------------
        val intent = intent
        val correo = intent.getStringExtra("correo")

        database = FirebaseDatabase.getInstance()


        val btnAgregar: Button = findViewById(R.id.addButton)

        btnAgregar.setOnClickListener {
            val intent = Intent(this, RegisterMascota::class.java)
            intent.putExtra("correo", correo)
            startActivity(intent)
        }

        val buttonIds = listOf(R.id.imageView9)
        for (i in 0 until buttonIds.size) {
            val button = findViewById<AppCompatImageView>(buttonIds[i])
            button.setOnClickListener {
                cargarMascota(correo, i + 1)
            }
        }
    }

    private fun cargarMascota(correo: String?, numMas: Int) {
        correo?.let { userEmail ->
            val userRef = database.getReference("Users").orderByChild("correo").equalTo(userEmail)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userId = dataSnapshot.children.firstOrNull()?.key
                        userId?.let { uid ->
                            val refMas = database.getReference("Mascotas").orderByChild("idUser").equalTo(uid)
                            refMas.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(mascotaSnapshot: DataSnapshot) {
                                    val mascotas = mutableListOf<Pair<String, Pet?>>()
                                    mascotaSnapshot.children.forEach { mascotaSnapshot ->
                                        mascotas.add((mascotaSnapshot.key ?: "") to mascotaSnapshot.getValue(Pet::class.java))
                                    }
                                    if (numMas <= mascotas.size) {
                                        val (mascotaId, mascota) = mascotas[numMas - 1]
                                        mascota?.let {
                                            val intent = Intent(this@MascotaActivity, InfoMascota::class.java)
                                            intent.putExtra("raza", mascota.raza)
                                            intent.putExtra("nombre", mascota.nombre)
                                            intent.putExtra("descripcion", mascota.descripcion)
                                            intent.putExtra("edad", mascota.edad)
                                            intent.putExtra("idMascota", mascotaId)
                                            intent.putExtra("correo", correo)
                                            startActivity(intent)
                                        }
                                    } else {
                                        Toast.makeText(this@MascotaActivity, "Sin mascotas", LENGTH_SHORT).show()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.e("MascotaActivity", "Error en la base de datos: ${error.message}")
                                }
                            })
                        }
                    } else {
                        Toast.makeText(this@MascotaActivity, "No se encontró ningún usuario", LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MascotaActivity", "Error en la base de datos: ${error.message}")
                }
            })
        }
    }

}