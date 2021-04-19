package com.sharkaboi.mediahub.modules.manga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sharkaboi.mediahub.databinding.FragmentMangaBinding
import com.sharkaboi.mediahub.modules.manga.adapters.MangaPagerAdapter

class MangaFragment : Fragment() {

    private var _binding: FragmentMangaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.vpManga.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vpAnimeAdapter = MangaPagerAdapter(this)
        binding.vpManga.adapter = vpAnimeAdapter
        TabLayoutMediator(binding.mangaTabLayout, binding.vpManga) { tab, position ->
            when (position) {
                0 -> tab.text = "Reading"
                1 -> tab.text = "Planned"
                2 -> tab.text = "Completed"
                3 -> tab.text = "On Hold"
                4 -> tab.text = "Dropped"
                5 -> tab.text = "All"
            }
        }.attach()
    }
}