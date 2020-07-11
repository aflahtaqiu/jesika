package id.koridor50.jesika.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import id.koridor50.jesika.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    private fun hideBottomNavigation(navigation: BottomNavigationView) {
        with(navigation) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate()
                    .alpha(0f)
                    .withEndAction { visibility = View.GONE }
                    .duration = 100
            }
        }
    }

    private fun showBottomNavigation(navigation: BottomNavigationView) {
        with(navigation) {
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 100
        }
    }
}