package mx.itson.edu.guardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class ProfileActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        //val name: TextView = findViewById(R.id.userName)
        val email: TextView = findViewById(R.id.userMail)

        database = FirebaseDatabase.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userEMail = currentUser.email

            email.text = userEMail
        } else {
            email.text = "Usuario no autenticado"
        }
    }
}