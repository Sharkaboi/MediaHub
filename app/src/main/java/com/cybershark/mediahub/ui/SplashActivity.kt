package com.cybershark.mediahub.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.getstarted.GetStartedActivity
import com.cybershark.mediahub.ui.modules.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoggedInToTrakt()
        setDarkOrLightTheme()
    }

    private fun setDarkOrLightTheme() {
        val themeOption = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("darkTheme", false)
        if (themeOption)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun checkLoggedInToTrakt() {
        TODO("Check refresh time in shared pref with current time and if past, go to another screen to get token again from user.")
        val loggedIn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("traktTokenSet", false)
        if (loggedIn) startMainActivity() else startGetStartedActivity()
    }

    private fun startGetStartedActivity() {
        startActivity(Intent(this, GetStartedActivity::class.java))
        finishAffinity()
        setCustomAnims()
    }

    private fun setCustomAnims() = overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
        setCustomAnims()
    }
}