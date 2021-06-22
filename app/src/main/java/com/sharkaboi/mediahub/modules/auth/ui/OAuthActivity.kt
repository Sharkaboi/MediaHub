package com.sharkaboi.mediahub.modules.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.cleanUpDrawable
import com.github.razir.progressbutton.showProgress
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.AppConstants
import com.sharkaboi.mediahub.data.api.retrofit.AuthService
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.common.util.openUrl
import com.sharkaboi.mediahub.databinding.ActivityAuthBinding
import com.sharkaboi.mediahub.modules.MainActivity
import com.sharkaboi.mediahub.modules.auth.vm.OAuthState
import com.sharkaboi.mediahub.modules.auth.vm.OAuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OAuthActivity : AppCompatActivity() {
    private val oAuthViewModel by viewModels<OAuthViewModel>()
    private var _binding: ActivityAuthBinding? = null
    private val binding: ActivityAuthBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindProgressButton(binding.btnRedirect)
        setObservers()
        setListeners()
    }

    override fun onDestroy() {
        binding.btnRedirect.cleanUpDrawable()
        super.onDestroy()
    }

    private fun setObservers() {
        oAuthViewModel.oAuthState.observe(this) { state ->
            Log.d(TAG, "state : $state")
            if (state is OAuthState.Idle || state is OAuthState.OAuthFailure) {
                binding.btnRedirect.isEnabled = true
            } else {
                binding.btnRedirect.isEnabled = false
                binding.btnRedirect.showProgress {
                    buttonText = "Loading"
                    gravity = DrawableButton.GRAVITY_TEXT_START
                    progressColorRes = R.color.white
                }
            }
            when (state) {
                is OAuthState.RedirectToAuth -> {
                    openUrl(
                        AuthService.getAuthTokenLink(
                            BuildConfig.clientId,
                            state.state,
                            state.codeChallenge
                        )
                    )
                }
                is OAuthState.OAuthSuccess -> {
                    redirectToMainAppFlow()
                }
                is OAuthState.OAuthFailure -> {
                    showToast(state.message, Toast.LENGTH_LONG)
                }
                else -> Unit
            }
        }
    }

    private fun setListeners() {
        binding.btnRedirect.setOnClickListener {
            oAuthViewModel.redirectToAuth()
        }
    }

    private fun redirectToMainAppFlow() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finishAffinity()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        Log.d(TAG, "onNewIntent uri :${uri.toString()}")
        if (uri != null && uri.toString().startsWith(AppConstants.oAuthDeepLinkUri)) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                oAuthViewModel.receivedAuthToken(code)
            }
        }
    }

    companion object {
        private const val TAG = "OAuthActivity"
    }
}