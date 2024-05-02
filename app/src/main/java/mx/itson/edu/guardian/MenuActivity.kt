package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import mx.itson.edu.guardian.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpTabBar()

        val intent1 = intent

        val correo = intent1.getStringExtra("correo")

        Log.d("TAG", "Correo electrónico del usuario: $correo")

        val MascotasButton : Button = findViewById(R.id.btnMascotas)

        MascotasButton.setOnClickListener {
            var intent: Intent = Intent( this, MascotaActivity::class.java)
            intent.putExtra("correo", correo) // Adjunta el correo electrónico como extra al Intent
            startActivity(intent)
        }

    }

    private fun setUpTabBar(){
        binding.bottomNavBar.setOnItemSelectedListener{
            when(it){
                R.id.nav_menu ->{

                    startActivity(Intent(this, MenuActivity::class.java))
                    true
                }

                R.id.nav_calendar ->{

                    startActivity(Intent(this, MenuReservacionesActivity::class.java))
                    true
                }

                R.id.nav_add ->{

                    startActivity(Intent(this, MascotaActivity::class.java))
                    true
                }

                R.id.nav_profile ->{

                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
            }
        }
    }
}