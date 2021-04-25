package com.sharkaboi.mediahub.modules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.bottomNavContainer)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.bottomNavContainer)
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnNavigationItemReselectedListener {
            if (navHostFragment != null && navHostFragment.childFragmentManager.backStackEntryCount >= 1) {
                navController.navigateUp()
            }
        }
    }
}