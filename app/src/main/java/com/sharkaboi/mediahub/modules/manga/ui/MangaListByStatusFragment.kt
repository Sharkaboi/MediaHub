package com.sharkaboi.mediahub.modules.manga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.databinding.FragmentMangaListByStatusBinding

class MangaListByStatusFragment : Fragment() {
    private var status: MangaStatus = MangaStatus.all

    private var _binding: FragmentMangaListByStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = MangaStatus.valueOf(
                it.getString(MangaListByStatusFragment.MANGA_STATUS_KEY) ?: MangaStatus.all.name
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaListByStatusBinding.inflate(inflater, container, false)
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

        private const val MANGA_STATUS_KEY = "status"

        @JvmStatic
        fun newInstance(status: MangaStatus) =
            MangaListByStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(MANGA_STATUS_KEY, status.name)
                }
            }
    }
}