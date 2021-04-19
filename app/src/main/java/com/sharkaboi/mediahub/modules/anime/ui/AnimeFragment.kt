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
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vpAnimeAdapter = AnimePagerAdapter(this)
        binding.vpAnime.adapter = vpAnimeAdapter
        TabLayoutMediator(binding.animeTabLayout, binding.vpAnime) { tab, position ->
            when (position) {
                0 -> tab.text = "Watching"
                1 -> tab.text = "Planned"
                2 -> tab.text = "Completed"
                3 -> tab.text = "On Hold"
                4 -> tab.text = "Dropped"
                5 -> tab.text = "All"
            }
        }.attach()
    }
}