package com.sharkaboi.mediahub.modules.manga_list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.startAnim
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.databinding.FragmentMangaBinding
import com.sharkaboi.mediahub.modules.manga_list.adapters.MangaPagerAdapter
import com.sharkaboi.mediahub.modules.manga_list.vm.MangaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MangaFragment : Fragment() {
    private lateinit var vpMangaAdapter: MangaPagerAdapter
    private var _binding: FragmentMangaBinding? = null
    private val binding get() = _binding!!
    private lateinit var onTabChanged: TabLayout.OnTabSelectedListener
    private lateinit var onPageChanged: OnPageChangeCallback
    private val mangaViewModel by activityViewModels<MangaViewModel>()
    private val navController by lazy { findNavController() }
    private val anim by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.fab_explode).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.vpManga.adapter = null
        binding.mangaTabLayout.removeAllTabs()
        binding.mangaTabLayout.removeOnTabSelectedListener(onTabChanged)
        binding.vpManga.unregisterOnPageChangeCallback(onPageChanged)
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpMangaAdapter = MangaPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.vpManga.adapter = vpMangaAdapter
        binding.mangaTabLayout.apply {
            MangaStatus.values().forEach {
                addTab(newTab().apply { text = it.getFormattedString(context) })
            }
        }
        onTabChanged = object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.vpManga.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) {
                childFragmentManager.findFragmentByTag("f${tab?.position ?: 0}")?.let {
                    if (it is MangaListByStatusFragment) {
                        it.scrollRecyclerView()
                    }
                }
            }
        }
        onPageChanged = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mangaViewModel.setMangaStatus(MangaStatus.values()[position])
                binding.mangaTabLayout.apply {
                    selectTab(getTabAt(position))
                }
            }
        }
        binding.vpManga.registerOnPageChangeCallback(onPageChanged)
        binding.mangaTabLayout.addOnTabSelectedListener(onTabChanged)
        binding.fabSearch.setOnClickListener {
            binding.fabSearch.isVisible = false
            binding.circleAnimeView.isVisible = true
            binding.circleAnimeView.startAnim(
                anim,
                onEnd = {
                    binding.circleAnimeView.isInvisible = true
                    navController.navigate(R.id.openMangaSearch)
                    binding.fabSearch.isVisible = true
                }
            )
        }
    }
}
