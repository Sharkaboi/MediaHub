package com.cybershark.mediahub.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.cybershark.mediahub.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val spFileName = "spMediaHub"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        sharedPreferences = getSharedPreferences(spFileName, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("darkMode", false))
            swDarkMode.isChecked = true
        swDarkMode.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    sharedPreferences.edit().putBoolean("darkMode", true).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    sharedPreferences.edit().putBoolean("darkMode", false).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}