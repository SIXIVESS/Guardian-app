package mx.itson.edu.guardian

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import mx.itson.edu.guardian.databinding.ActivityMascotaBinding


class MascotaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMascotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpTabBar()
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