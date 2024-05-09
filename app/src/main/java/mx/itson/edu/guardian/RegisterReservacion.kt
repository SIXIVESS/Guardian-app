package mx.itson.edu.guardian

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class RegisterReservacion : AppCompatActivity() {


    private lateinit var spinnerEmpresas: Spinner
    private lateinit var spinnerMascotas: Spinner

    private lateinit var fechaSeleccionadaTextView: TextView
    private lateinit var seleccionarFechaButton: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var reservacionRef: DatabaseReference
    private lateinit var empresaRef: DatabaseReference
    private lateinit var mascotaRef: DatabaseReference

    private var fechaSeleccionada: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_reservacion)

        spinnerEmpresas = findViewById(R.id.spinnerEmpresas)
        spinnerMascotas = findViewById(R.id.spinnerMascotas)

        fechaSeleccionadaTextView = findViewById(R.id.fechaSeleccionadaTextView)
        seleccionarFechaButton = findViewById(R.id.seleccionarFechaButton)

        seleccionarFechaButton.setOnClickListener {
            mostrarDatePickerDialog()
        }


        //fechaSeleccionada = findViewById(R.id.fechaSeleccionada)

        database = FirebaseDatabase.getInstance()
        reservacionRef = database.getReference("Reservaciones")
        empresaRef = database.getReference("Empresas")
        mascotaRef = database.getReference("Mascotas")

        empresaRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val empresas = mutableListOf<String>()
                for (empresaSnapshot in snapshot.children) {
                    val nombreEmpresa = empresaSnapshot.child("Nombre").getValue(String::class.java)
                    nombreEmpresa?.let { empresas.add(it) }
                }
                val empresaAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, empresas)
                empresaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEmpresas.adapter = empresaAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error de lectura de la base de datos
            }
        })

        mascotaRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mascotas = mutableListOf<String>()
                for (mascotaSnapshot in snapshot.children) {
                    val nombreMascota = mascotaSnapshot.child("nombre").getValue(String::class.java)
                    nombreMascota?.let { mascotas.add(it) }
                }
                val mascotaAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, mascotas)
                mascotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerMascotas.adapter = mascotaAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error de lectura de la base de datos
            }
        })

        val reservarButton = findViewById<Button>(R.id.btnAgregar)
        reservarButton.setOnClickListener {
            val empresaSeleccionada = spinnerEmpresas.selectedItem.toString()
            val mascotaSeleccionada = spinnerMascotas.selectedItem.toString()
            // Aquí deberías tener la fecha seleccionada previamente asignada a fechaSeleccionada

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid ?: ""

            val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaFormateada = formatoFecha.format(fechaSeleccionada)

            // Crear un nuevo nodo para la reservación en la base de datos
            val nuevaReservacionRef = reservacionRef.push()
            val nuevaReservacion = hashMapOf(
                "userId" to userId,
                "Empresa" to empresaSeleccionada,
                "Mascota" to mascotaSeleccionada,
                "FechaReservacion" to fechaFormateada
                // Aquí debes convertir la fecha a un formato adecuado para almacenarla en la base de datos
            )

            nuevaReservacionRef.setValue(nuevaReservacion)
                .addOnSuccessListener {
                    // Manejar el éxito de la operación
                    finish()
                }
                .addOnFailureListener {
                    // Manejar el fallo de la operación
                }
        }


    }
    private fun mostrarDatePickerDialog() {
        // Obtener la fecha actual
        val calendario: Calendar = Calendar.getInstance()
        val año: Int = calendario.get(Calendar.YEAR)
        val mes: Int = calendario.get(Calendar.MONTH)
        val día: Int = calendario.get(Calendar.DAY_OF_MONTH)

        // Crear un DatePickerDialog y mostrarlo
        val datePickerDialog = DatePickerDialog(this,
            { view, year, month, dayOfMonth -> // Cuando el usuario selecciona una fecha, actualiza el TextView con la fecha seleccionada
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                fechaSeleccionada = calendar.time

                var fechaSeleccionada = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                fechaSeleccionadaTextView.setText("Fecha seleccionada: $fechaSeleccionada")
            }, año, mes, día
        )

        // Mostrar el diálogo de selección de fecha
        datePickerDialog.show()
    }
}