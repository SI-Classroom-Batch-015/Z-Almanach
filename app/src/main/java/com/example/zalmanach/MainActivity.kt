package com.example.zalmanach

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.zalmanach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Android Studio SHADOW-BUG MaterialBottomBar aufgelöst
//        binding.bottomNavigationView.background = null

        // Verknüpft die Bottom-Navigation-Bar mit dem Navigation Controller des NavHost-Fragments
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.playFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.dbzDetailFragment -> binding.bottomNavigationView.visibility = View.GONE

                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })
    }

    // Wegen Wiederverwendbarkeit, hier Implementiert und rufe sie in benötigten Fragmenten auf
    fun showBottomNav(show: Boolean) {
        binding.bottomNavigationView.visibility = if (show) View.VISIBLE else View.GONE
    }
}
