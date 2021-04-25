package com.sharkaboi.mediahub.modules.anime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.databinding.FragmentAnimeBinding
import com.sharkaboi.mediahub.modules.anime.adapters.AnimePagerAdapter


class AnimeFragment : Fragment() {

    private lateinit var vpAnimeAdapter: AnimePagerAdapter
    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var onTabChanged: TabLayout.OnTabSelectedListener
    private lateinit var onPageChanged: OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.vpAnime.adapter = null
        binding.animeTabLayout.removeAllTabs()
        binding.animeTabLayout.removeOnTabSelectedListener(onTabChanged)
        binding.vpAnime.unregisterOnPageChangeCallback(onPageChanged)
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpAnimeAdapter = AnimePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.vpAnime.adapter = vpAnimeAdapter
        binding.animeTabLayout.apply {
            addTab(newTab().apply { text = "Watching" })
            addTab(newTab().apply { text = "Planned" })
            addTab(newTab().apply { text = "Completed" })
            addTab(newTab().apply { text = "On Hold" })
            addTab(newTab().apply { text = "Dropped" })
            addTab(newTab().apply { text = "All" })
        }
        onTabChanged = object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.vpAnime.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) {
                childFragmentManager.findFragmentByTag("f${tab?.position ?: 0}")?.let {
                    if (it is AnimeListByStatusFragment) {
                        it.scrollRecyclerView()
                        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
                            ?.let { bottomNav ->
                                val layoutParams =
                                    bottomNav.layoutParams as CoordinatorLayout.LayoutParams?
                                val bottomViewNavigationBehavior =
                                    layoutParams?.behavior as HideBottomViewOnScrollBehavior?
                                bottomViewNavigationBehavior?.slideUp(bottomNav)
                            }
                    }
                }
            }
        }
        onPageChanged = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.animeTabLayout.apply {
                    selectTab(getTabAt(position))
                }
            }
        }
        binding.animeTabLayout.addOnTabSelectedListener(onTabChanged)
        binding.vpAnime.registerOnPageChangeCallback(onPageChanged)
    }


    companion object {
        private const val TAG = "AnimeFragment"
    }
}