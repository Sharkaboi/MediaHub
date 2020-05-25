package com.cybershark.mediahub.ui.settings.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.repository.sharedprefs.SharedPrefConstants
import com.cybershark.mediahub.ui.settings.viewmodels.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val sharedPreferences by lazy { getSharedPreferences(spFileName, Context.MODE_PRIVATE) }
    private val spFileName by lazy { SharedPrefConstants.spFileName }
    private val settingsViewModel by lazy { ViewModelProvider(this).get(SettingsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupToolBar()

        setCurrentThemeMode()
        /*settingsViewModel.themeOptionLiveData.observe(this, Observer {
            swDarkMode.isChecked = it
        })
*/
        swDarkMode.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    setDarkThemeOption(true)
                    setDarkMode()
                }
                false -> {
                    setDarkThemeOption(false)
                    setLightMode()
                }
            }
        }
    }

    private fun setCurrentThemeMode() {
        if (getThemeOption()) swDarkMode.isChecked = true
    }

    private fun setDarkThemeOption(option: Boolean) =
        sharedPreferences.edit().putBoolean("darkMode", option).apply()

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun finish() {
        super.finish()
        setActivityExitAnims()
    }

    private fun getThemeOption() = sharedPreferences.getBoolean("darkMode", false)

    private fun setDarkMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    private fun setLightMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    private fun setActivityExitAnims() =
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
}