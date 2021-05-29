package com.sharkaboi.mediahub.common.views.image_slider

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.sharkaboi.mediahub.databinding.FragmentImageSliderBinding


class ImageSliderFragment : DialogFragment() {
    private var _binding: FragmentImageSliderBinding? = null
    private val binding get() = _binding!!
    private val args: ImageSliderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.vpImages.adapter = null
        binding.dotsIndicator.removeAllTabs()
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onResume() {
        dialog?.window?.decorView?.setPadding(0)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnClose.setOnClickListener {
                dialog?.dismiss()
            }
            val adapter =
                ImageSliderAdapter(args.imagesList.toList(), childFragmentManager, lifecycle)
            vpImages.adapter = adapter
            TabLayoutMediator(dotsIndicator, vpImages, true) { _, _ -> }.attach()
        }
    }
}