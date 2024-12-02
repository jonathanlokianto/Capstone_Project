package com.dicoding.capstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_bottom_nav) as NavHostFragment

        // Get the NavController from the NavHostFragment
        navController = navHostFragment.navController

        // Get the BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        // Set up the BottomNavigationView with the NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Handle fragment changes (optional)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> {
                    // Logic for Home fragment
                }
                R.id.nav_article -> {
                    // Logic for Article fragment
                }
                R.id.nav_profile -> {
                    // Logic for Profile fragment
                }
            }
        }
    }
}