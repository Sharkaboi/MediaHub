package com.sharkaboi.mediahub.modules.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.util.forceLaunchInBrowser
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.databinding.ActivityMainBinding
import com.sharkaboi.mediahub.modules.auth.ui.OAuthActivity
import com.sharkaboi.mediahub.modules.main.vm.MainViewModel
import com.sharkaboi.mediahub.modules.main.vm.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configBottomNav()
        setListeners()
        setObservers()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.dataString?.matches(ApiConstants.appAcceptedDeepLinkRegex) == true) {
            val result = navController.handleDeepLink(intent)
            if (result.not()) {
                handleNonSupportedIntent(intent)
            }
        } else {
            handleNonSupportedIntent(intent)
        }
    }

    private fun handleNonSupportedIntent(intent: Intent?) {
        val url = intent?.dataString
        Timber.d("Can't handle intent $url")
        url?.let { forceLaunchInBrowser(it) }
    }

    private fun configBottomNav() {
        navController = findNavController(R.id.bottomNavContainer)
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setListeners() {
        binding.bottomNav.setOnItemReselectedListener {
            navController.popBackStack(it.itemId, false)
        }
    }

    private fun setObservers() {
        observe(mainViewModel.uiState) { state: MainViewState ->
            when (state) {
                MainViewState.AuthTokenInValid -> redirectToOAuthFlow()
                MainViewState.AuthTokenValid -> Unit
            }
        }
    }

    private fun redirectToOAuthFlow() {
        startActivity(Intent(this, OAuthActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
        Timber.d("redirectToOAuthFlow")
    }
}
