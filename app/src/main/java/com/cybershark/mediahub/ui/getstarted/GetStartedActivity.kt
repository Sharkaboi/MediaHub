package com.cybershark.mediahub.ui.getstarted

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.api.APIConstants
import com.cybershark.mediahub.ui.modules.MainActivity
import com.cybershark.mediahub.util.Status
import com.cybershark.mediahub.util.getStagingAuthURI
import kotlinx.android.synthetic.main.activity_get_started.*

class GetStartedActivity : AppCompatActivity() {

    companion object {
        const val TAG = "GetStartedActivity"
    }

    private val getStartedViewModel by lazy { ViewModelProvider(this).get(GetStartedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
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
            contentLoading.visibility = View.VISIBLE
            Log.e(TAG, "addCallbackListener: Received deep uri intent with data $uri")
            val code = uri.getQueryParameter("code")
            getAccessTokenFromAPIWithCode(code)
            setAuthSuccessListener()
        }
    }

    private fun setAuthSuccessListener() {
        getStartedViewModel.authStatus.observe(this, Observer {
            when (it) {
                Status.COMPLETED -> startMainActivity()
                Status.ERROR -> {
                    contentLoading.visibility = View.GONE
                    Toast.makeText(this, "An Error Occurred!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getAccessTokenFromAPIWithCode(code: String?) = getStartedViewModel.getTokensFromAuthCode(code)

    private fun setupSlideReels() {
        vpSlideShow.apply {
            adapter = GetStartedAdapter(getReelList())
        }
        dots_indicator.setViewPager2(vpSlideShow)
    }

    private fun getReelList(): List<Pair<Int, String>> = listOf(
        Pair(R.drawable.ic_trakt_detailed, "Sync Movies, Anime, Series with Trakt"),
        Pair(R.drawable.ic_mal_detailed, "Sync Manga with MyAnimeList"),
        Pair(R.drawable.ic_stats_detailed, "View Statistics of your choices")
    )

    private fun setupListeners() {
        btnGetStarted.setOnClickListener { authWithTrakt() }
    }

    private fun authWithTrakt() = startActivity(Intent(Intent.ACTION_VIEW, getStagingAuthURI(this)))

    private fun setCustomAnims() = overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
        setCustomAnims()
    }
}