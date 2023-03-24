package com.example.reciperanger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.reciperanger.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHost.navController

        setupToolBar()
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { controller, destination, args ->
            if (destination.id != R.id.homeFragment && destination.id != R.id.profileFragment && destination.id != R.id.favoriteFragment && destination.id != R.id.searchFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.hide()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.profileFragment,
                R.id.favoriteFragment,
                R.id.searchFragment
            )
        )

        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }
}