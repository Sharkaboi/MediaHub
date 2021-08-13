package com.sharkaboi.mediahub.common.views.image_slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.databinding.FragmentFullScreenImageBinding

class FullScreenImageFragment : Fragment() {
    private lateinit var imageUrl: String
    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(IMAGE_URL_KEY) ?: String.emptyString
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullScreenImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivImage.load(imageUrl) {
            crossfade(true)
            error(R.drawable.ic_broken_image)
            fallback(R.drawable.ic_broken_image)
        }
    }

    companion object {
        private const val IMAGE_URL_KEY = "imageUrl"

        @JvmStatic
        fun newInstance(imageUrl: String) =
            FullScreenImageFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URL_KEY, imageUrl)
                }
            }
    }
}
