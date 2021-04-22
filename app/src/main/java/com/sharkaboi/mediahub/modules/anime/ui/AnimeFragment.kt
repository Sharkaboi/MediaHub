package com.sharkaboi.mediahub.modules.anime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sharkaboi.mediahub.databinding.FragmentAnimeBinding
import com.sharkaboi.mediahub.modules.anime.adapters.AnimePagerAdapter


class AnimeFragment : Fragment() {

    private var tabLayoutMediator: TabLayoutMediator? = null
    private lateinit var vpAnimeAdapter: AnimePagerAdapter
    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.vpAnime.adapter = null
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpAnimeAdapter = AnimePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.vpAnime.adapter = vpAnimeAdapter
        tabLayoutMediator =
            TabLayoutMediator(binding.animeTabLayout, binding.vpAnime) { tab, position ->
                when (position) {
                    0 -> tab.text = "Watching"
                    1 -> tab.text = "Planned"
                    2 -> tab.text = "Completed"
                    3 -> tab.text = "On Hold"
                    4 -> tab.text = "Dropped"
                    5 -> tab.text = "All"
                }
            }
        tabLayoutMediator?.attach()
//        binding.animeTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                vpAnimeAdapter.createFragment(tab?.position ?: 0)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                childFragmentManager.findFragmentByTag("f${tab?.position ?: 0}")?.view?.findViewById<RecyclerView>(
//                    R.id.rvAnimeByStatus
//                )?.smoothScrollToPosition(0)
//                activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.let {
//                    val layoutParams = it.layoutParams as CoordinatorLayout.LayoutParams
//                    val bottomViewNavigationBehavior =
//                        layoutParams.behavior as HideBottomViewOnScrollBehavior?
//                    bottomViewNavigationBehavior?.slideUp(it)
//                }
//            }
//        })
    }

    companion object {
        private const val TAG = "AnimeFragment"
    }
}