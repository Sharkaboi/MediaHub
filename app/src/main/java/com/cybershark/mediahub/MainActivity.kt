package com.cybershark.mediahub

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.cybershark.mediahub.data.repository.sharedprefs.SharedPrefConstants
import com.cybershark.mediahub.ui.settings.views.SettingsActivity
import com.cybershark.mediahub.util.InternetConnectionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sharedPreferences by lazy { getSharedPreferences(spFileName, Context.MODE_PRIVATE) }
    private val spFileName by lazy { SharedPrefConstants.spFileName }
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(this::class.toString(), "internet active : ${InternetConnectionManager.isInternetActive()}")

        setTheme(R.style.AppTheme)

        if (getThemeOption()) setDarkMode() else setLightMode()

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list,
                R.id.navigation_series,
                R.id.navigation_movies,
                R.id.navigation_manga,
                R.id.navigation_stats
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setOnNavigationItemSelectedListener {
            if (it.itemId != navView.selectedItemId)
                it.onNavDestinationSelected(navController)
            true
        }
    }

    private fun getThemeOption() = sharedPreferences.getBoolean("darkMode", false)

    private fun setDarkMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    private fun setLightMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    private fun openSettingsActivity() = startActivity(Intent(this, SettingsActivity::class.java))

    private fun setActivityAnims() =
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (SystemClock.elapsedRealtime() - lastClickTime > 1000) {
            lastClickTime = SystemClock.elapsedRealtime()
            openSettingsActivity()
            setActivityAnims()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
