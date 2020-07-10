package com.cybershark.mediahub.ui.modules

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.modules.settings.views.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupBottomNav()
    }

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

    private fun setupBottomNav() {
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

        nav_view.setOnNavigationItemSelectedListener {
            if (it.itemId != nav_view.selectedItemId)
                it.onNavDestinationSelected(navController)
            true
        }
    }

    private fun openSettingsActivity() = startActivity(Intent(this, SettingsActivity::class.java))

    private fun setActivityAnims() = overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

}
