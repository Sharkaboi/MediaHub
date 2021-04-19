package com.sharkaboi.mediahub.modules.anime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.databinding.FragmentAnimeListByStatusBinding

class AnimeListByStatusFragment : Fragment() {
    private var status: AnimeStatus = AnimeStatus.all

    private var _binding: FragmentAnimeListByStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = AnimeStatus.valueOf(it.getString(ANIME_STATUS_KEY) ?: AnimeStatus.all.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvStatus.text = status.name
    }

    companion object {
        private const val ANIME_STATUS_KEY = "status"

        @JvmStatic
        fun newInstance(status: AnimeStatus) =
            AnimeListByStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ANIME_STATUS_KEY, status.name)
                }
            }
    }
}