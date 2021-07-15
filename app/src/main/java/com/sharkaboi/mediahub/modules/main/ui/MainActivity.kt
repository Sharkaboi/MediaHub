package com.sharkaboi.mediahub.modules.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.startAnim
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
    private val anim by lazy {
        AnimationUtils.loadAnimation(this, R.anim.fab_explode).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

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
        navController.handleDeepLink(intent)
    }

    private fun configBottomNav() {
        navController = findNavController(R.id.bottomNavContainer)
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setListeners() {
        setVisibilityAndListeners(binding.bottomNav.selectedItemId)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setVisibilityAndListeners(destination.id)
        }
        binding.bottomNav.setOnItemReselectedListener {
            navController.popBackStack(it.itemId, false)
            setVisibilityAndListeners(it.itemId)
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

    fun setVisibilityAndListeners(@IdRes id: Int) {
        val isAnimeItem =
            id == R.id.anime_item && navController.currentDestination?.id == R.id.anime_item
        val isMangaItem =
            id == R.id.manga_item && navController.currentDestination?.id == R.id.manga_item
        binding.fabSearch.isVisible = isAnimeItem || isMangaItem
        binding.fabSearch.setOnClickListener {
            binding.fabSearch.isVisible = false
            binding.circleAnimeView.isVisible = true
            binding.circleAnimeView.startAnim(anim) {
                binding.fabSearch.isVisible = isAnimeItem || isMangaItem
                binding.circleAnimeView.isVisible = false
                when {
                    isAnimeItem -> navController.navigate(R.id.openAnimeSearch)
                    isMangaItem -> navController.navigate(R.id.openMangaSearch)
                }
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
