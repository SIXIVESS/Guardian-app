package mx.itson.edu.guardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Tag

class MenuReservacionesActivity : AppCompatActivity() {

    lateinit var myListView: ListView
    lateinit var myArrayList: ArrayList<String>

    private lateinit var database: FirebaseDatabase
    private lateinit var reservacionRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_reservaciones)

        myListView = findViewById(R.id.listView1)
        myArrayList = ArrayList()

        database = FirebaseDatabase.getInstance()

        reservacionRef = database.getReference("Reservaciones")

        reservacionRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                myArrayList.clear()
                for (snapshot in dataSnapshot.children) {
                    val fechaReservacion = snapshot.child("FechaReservacion").getValue(String::class.java)
                    val empresa = snapshot.child("Empresa").getValue(String::class.java)
                    val mascota = snapshot.child("Mascota").getValue(String::class.java)

                    val reservacionInfo = "Fecha: $fechaReservacion\nEmpresa: $empresa\nMascota: $mascota"
                    myArrayList.add(reservacionInfo)
                }
                val myArrayAdapter = ArrayAdapter(this@MenuReservacionesActivity, android.R.layout.simple_list_item_1, myArrayList)
                myListView.adapter = myArrayAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    companion object {
        private const val TAG = "MenuReservacionesActivity"
    }

}