package com.sharkaboi.mediahub.modules.splash.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.modules.MainActivity
import com.sharkaboi.mediahub.modules.auth.ui.OAuthActivity
import com.sharkaboi.mediahub.modules.splash.vm.SplashState
import com.sharkaboi.mediahub.modules.splash.vm.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        splashViewModel.splashState.observe(this) { state ->
            Timber.d(state.toString())
            when (state) {
                is SplashState.LoginExpired -> {
                    redirectToOAuthFlow()
                }
                is SplashState.FetchComplete -> {
                    setDefaultNightMode(
                        if (state.isDarkMode) {
                            MODE_NIGHT_YES
                        } else {
                            MODE_NIGHT_NO
                        }
                    )
                    if (!state.isAccessTokenValid) {
                        redirectToOAuthFlow()
                    } else if (state.hasExpired) {
                        splashViewModel.refreshToken()
                    } else {
                        redirectToMainAppFlow()
                    }
                }
                else -> Unit
            }
        }
    }

    private fun redirectToMainAppFlow() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
        Timber.d("redirectToMainAppFlow")
    }

    private fun redirectToOAuthFlow() {
        startActivity(Intent(this, OAuthActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
        Timber.d("redirectToOAuthFlow")
    }
}
