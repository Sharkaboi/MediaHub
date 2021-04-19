package com.sharkaboi.mediahub.modules.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.common.extensions.isShowing
import com.sharkaboi.mediahub.common.extensions.shortSnackBar
import com.sharkaboi.mediahub.databinding.FragmentProfileBinding
import com.sharkaboi.mediahub.modules.profile.vm.ProfileStates
import com.sharkaboi.mediahub.modules.profile.vm.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        profileViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.progressBar.isShowing = uiState is ProfileStates.Loading
            when (uiState) {
                is ProfileStates.Idle -> {
                    profileViewModel.getUserDetails()
                }
                is ProfileStates.FetchSuccess -> {
                    setData(uiState.userDetailsResponse)
                }
                is ProfileStates.ProfileFailure -> {
                    binding.root.shortSnackBar(uiState.message)
                }
                else -> Unit
            }
        }
    }

    private fun setData(userDetailsResponse: UserDetailsResponse) {
        binding.apply {
            fabMenu.setOnClickListener(toggleMenu)
            fabAbout.setOnClickListener(showAboutDialog)
            tvTemp.text = userDetailsResponse.toString()
        }
    }

    private val showAboutDialog = { v: View ->
        toggleMenu(v)
        binding.root.shortSnackBar("About")
    }

    private var isMenuOpen = false
    private val toggleMenu = { _: View ->
        binding.apply {
            if (isMenuOpen) {
                fabAbout.apply {
                    isVisible = isMenuOpen
                    startAnimation(translateDown)
                }
                fabLogOut.apply {
                    isVisible = isMenuOpen
                    startAnimation(translateDown)
                }
                fabSettings.apply {
                    isVisible = isMenuOpen
                    startAnimation(translateDown)
                }
                fabMenu.apply {
                    startAnimation(rotateClose)
                    setImageResource(R.drawable.ic_menu_closed)
                }
            } else {
                fabAbout.apply {
                    isVisible = isMenuOpen
                    startAnimation(translateUp)
                }
                fabLogOut.apply {
                    isVisible = isMenuOpen
                    startAnimation(translateUp)
                }
                fabSettings.apply {
                    isVisible = isMenuOpen
                    startAnimation(translateUp)
                }
                fabMenu.apply {
                    startAnimation(rotateOpen)
                    setImageResource(R.drawable.ic_menu_open)
                }
            }
        }
        isMenuOpen = !isMenuOpen
    }

    private val rotateOpen by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    private val translateUp by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.translate_up
        )
    }
    private val translateDown by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.translate_down
        )
    }
}