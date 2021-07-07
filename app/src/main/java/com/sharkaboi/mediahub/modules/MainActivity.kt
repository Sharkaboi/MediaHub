package com.sharkaboi.mediahub.modules

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.startAnim
import com.sharkaboi.mediahub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val anim by lazy {
        AnimationUtils.loadAnimation(this, R.anim.fab_explode).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.bottomNavContainer)
        binding.bottomNav.setupWithNavController(navController)
        setVisibilityAndListeners(binding.bottomNav.selectedItemId)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setVisibilityAndListeners(destination.id)
        }
        binding.bottomNav.setOnNavigationItemReselectedListener {
            navController.popBackStack(it.itemId, false)
            setVisibilityAndListeners(it.itemId)
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
}
