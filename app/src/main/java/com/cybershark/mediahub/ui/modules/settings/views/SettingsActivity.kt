package com.cybershark.mediahub.ui.modules.settings.views

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.cybershark.mediahub.BuildConfig
import com.cybershark.mediahub.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setActivityAnims()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            setupListeners()
            setVersionCode()
        }

        private fun setupListeners() {
            setupTraktOption()
            setupTraktSyncInterval()
            setupMALOption()
            setSyncGooglePrefs()
            setDarkThemeSwitchListener()
        }

        private fun setupMALOption() {
            //kdsfkjsdhlfksd
            //TODO("Not yet implemented")
        }

        private fun setupTraktSyncInterval() {
            //TODO("Not yet implemented")
        }

        private fun setupTraktOption() {
            val tokenSet = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("traktTokenSet", false)
            findPreference<Preference>("syncTrakt")?.apply {
                summary = if (tokenSet) getString(R.string.logged_in) else getString(R.string.logged_out)
                setOnPreferenceClickListener { openTraktDialog(); true }
            }
        }

        private fun openTraktDialog() {
            //TODO("Not yet implemented")
        }

        private fun setDarkThemeSwitchListener() {
            findPreference<Preference>("darkTheme")?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
        }

        private fun setSyncGooglePrefs() {
            val syncPrefs = findPreference<Preference>("syncGoogle")
            syncPrefs?.isEnabled = android.os.Build.VERSION.SDK_INT >= 28
            syncPrefs?.setOnPreferenceClickListener {
                activity?.startActivity(Intent(Settings.ACTION_PRIVACY_SETTINGS))
                true
            }
        }

        private fun setVersionCode() {
            findPreference<Preference>("versionCode")?.summary = BuildConfig.VERSION_CODE.toFloat().toString()
        }
    }

    private fun setActivityAnims() = overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
}