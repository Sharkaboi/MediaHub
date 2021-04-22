package com.sharkaboi.mediahub.modules.anime_details.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.databinding.FragmentAnimeDetailsBinding
import com.sharkaboi.mediahub.databinding.FragmentAnimeListByStatusBinding
import java.util.*

class AnimeDetailsFragment : Fragment() {
    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: AnimeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvAnimeName.text = args.animeId.toString()
    }
}