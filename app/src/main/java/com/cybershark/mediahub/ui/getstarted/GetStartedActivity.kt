package com.cybershark.mediahub.ui.getstarted

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.api.APIConstants
import com.cybershark.mediahub.databinding.ActivityGetStartedBinding
import com.cybershark.mediahub.ui.modules.MainActivity
import com.cybershark.mediahub.util.STATUS
import com.cybershark.mediahub.util.getStagingAuthURI

class GetStartedActivity : AppCompatActivity() {

    companion object {
        const val TAG = "GetStartedActivity"
    }

    private val binding by lazy { ActivityGetStartedBinding.inflate(layoutInflater) }
    private val getStartedViewModel by lazy { ViewModelProvider(this).get(GetStartedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
        setupSlideReels()
    }

    override fun onResume() {
        super.onResume()
        addCallbackListener()
    }

    private fun addCallbackListener() {
        val uri = intent.data
        if (uri != null && uri.toString().contains(APIConstants.DEEP_LINK_CALLBACK_URI)) {
            binding.contentLoading.isVisible = true
            Log.e(TAG, "addCallbackListener: Received deep uri intent with data $uri")
            val code = uri.getQueryParameter("code")
            getAccessTokenFromAPIWithCode(code)
            setAuthSuccessListener()
        }
    }

    private fun setAuthSuccessListener() {
        getStartedViewModel.authStatus.observe(this, Observer {
            when (it) {
                is STATUS.COMPLETED -> startMainActivity(it.message)
                is STATUS.ERROR -> {
                    binding.contentLoading.isGone = true
                    Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getAccessTokenFromAPIWithCode(code: String?) = getStartedViewModel.getTokensFromAuthCode(code)

    private fun setupSlideReels() {
        binding.vpSlideShow.apply {
            adapter = GetStartedAdapter(getReelList())
            binding.dotsIndicator.setViewPager2(this)
        }
    }

    private fun getReelList(): List<Pair<Int, String>> = listOf(
        Pair(R.drawable.ic_trakt_detailed, "Sync Movies, Anime, Series with Trakt"),
        Pair(R.drawable.ic_mal_detailed, "Sync Manga with MyAnimeList"),
        Pair(R.drawable.ic_stats_detailed, "View Statistics of your choices")
    )

    private fun setupListeners() {
        binding.btnGetStarted.setOnClickListener { authWithTrakt() }
    }

    private fun authWithTrakt() = startActivity(Intent(Intent.ACTION_VIEW, getStagingAuthURI(this)))

    private fun setCustomAnims() = overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    private fun startMainActivity(message: String) {
        Log.e(TAG, "startMainActivity: $message")
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
        setCustomAnims()
    }
}