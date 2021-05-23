package com.sharkaboi.mediahub.modules.manga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.databinding.FragmentMangaBinding
import com.sharkaboi.mediahub.modules.manga.adapters.MangaPagerAdapter

class MangaFragment : Fragment() {

    private lateinit var vpMangaAdapter: MangaPagerAdapter
    private var _binding: FragmentMangaBinding? = null
    private val binding get() = _binding!!
    private lateinit var onTabChanged: TabLayout.OnTabSelectedListener
    private lateinit var onPageChanged: ViewPager2.OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
                addTab(newTab().apply { text = it.getFormattedString() })
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
        onPageChanged = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.mangaTabLayout.apply {
                    selectTab(getTabAt(position))
                }
            }
        }
        binding.mangaTabLayout.addOnTabSelectedListener(onTabChanged)
        binding.vpManga.registerOnPageChangeCallback(onPageChanged)
    }

    companion object {
        private const val TAG = "MangaFragment"
    }
}