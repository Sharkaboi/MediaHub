package com.sharkaboi.mediahub.modules.profile.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.common.util.MPAndroidChartValueFormatter
import com.sharkaboi.mediahub.databinding.FragmentProfileBinding
import com.sharkaboi.mediahub.modules.anime_details.ui.AnimeDetailsFragmentDirections
import com.sharkaboi.mediahub.modules.profile.vm.ProfileStates
import com.sharkaboi.mediahub.modules.profile.vm.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }

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
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            profileContent.ivProfileImage.load(R.drawable.ic_profile_placeholder) {
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
            profileContent.ibSettings.setOnClickListener(showSettings)
        }
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
            profileContent.ibCollapseDetails.setOnClickListener(toggleDetailsCard)
            toggleDetailsCard.onClick(null)
            profileContent.apply {
                ivProfileImage.load(userDetailsResponse.profilePicUrl) {
                    crossfade(300)
                    transformations(RoundedCornersTransformation(10f))
                    placeholder(R.drawable.ic_profile_placeholder)
                    error(R.drawable.ic_profile_placeholder)
                }
                ivProfileImage.setOnClickListener {
                    val action =
                        AnimeDetailsFragmentDirections.openImages(arrayOf(userDetailsResponse.profilePicUrl))
                    navController.navigate(action)
                }
                tvName.text = userDetailsResponse.name
                ibShare.setOnClickListener {
                    showShareDialog(userDetailsResponse.name)
                }
                profileDetailsCardContent.apply {
                    tvBirthDay.text =
                        userDetailsResponse.birthday?.tryParseDateTime()?.formatDateDMY() ?: "N/A"
                    tvGender.text =
                        userDetailsResponse.gender?.capitalizeFirst()
                            ?: "N/A"
                    tvJoinedAt.text =
                        userDetailsResponse.joinedAt.tryParseDateTime()?.formatDateDMY() ?: "N/A"
                    tvLocation.text = userDetailsResponse.location ?: "N/A"
                    tvTimeZone.text = userDetailsResponse.timeZone ?: "N/A"
                    tvSupporter.text =
                        if (userDetailsResponse.isSupporter == null || !userDetailsResponse.isSupporter) {
                            "No"
                        } else {
                            "Yes"
                        }
                }
                if (userDetailsResponse.animeStatistics == null) {
                    tvStatsEmptyHint.isVisible = true
                } else {
                    profileStatsContent.root.isVisible = true
                    profileStatsContent.apply {
                        tvEpisodes.text =
                            ("${userDetailsResponse.animeStatistics.numEpisodes.toInt()} episodes")
                        tvDaysWatched.text =
                            ("${userDetailsResponse.animeStatistics.numDaysCompleted.toInt()} days")
                        tvReWatchCount.text =
                            ("Re-watched ${userDetailsResponse.animeStatistics.numTimesReWatched.toInt()} times")
                        tvMeanScore.text =
                            userDetailsResponse.animeStatistics.meanScore.roundOfString()
                        pieItemCounts.apply {
                            val pieChart = this
                            val entries = listOf(
                                PieEntry(
                                    userDetailsResponse.animeStatistics.numItemsCompleted.toFloat(),
                                    "Completed"
                                ),
                                PieEntry(
                                    userDetailsResponse.animeStatistics.numItemsOnHold.toFloat(),
                                    "On Hold"
                                ),
                                PieEntry(
                                    userDetailsResponse.animeStatistics.numItemsDropped.toFloat(),
                                    "Dropped"
                                ),
                                PieEntry(
                                    userDetailsResponse.animeStatistics.numItemsWatching.toFloat(),
                                    "Watching"
                                ),
                                PieEntry(
                                    userDetailsResponse.animeStatistics.numItemsPlanToWatch.toFloat(),
                                    "Planned"
                                )
                            )
                            if (entries.count { it.value == 0f } != entries.count()) {
                                val pieData = PieData(PieDataSet(entries, "").apply {
                                    colors = listOf(
                                        "#2ecc71".parseRGB(),
                                        "#ffa500".parseRGB(),
                                        "#e74c3c".parseRGB(),
                                        "#3498db".parseRGB(),
                                        "#5634eb".parseRGB(),
                                    )
                                    valueTextSize = 14f
                                    valueTextColor = Color.WHITE
                                    valueFormatter = MPAndroidChartValueFormatter()
                                })
                                data = pieData
                                setTouchEnabled(false)
                                setDrawEntryLabels(false)
                                setNoDataTextColor("#ba68c8".parseRGB())
                                val themeColor =
                                    MaterialColors.getColor(pieChart, R.attr.colorOnSurface)
                                legend.textColor = themeColor
                                setHoleColor(Color.TRANSPARENT)
                                description.text = ""
                                description.isEnabled = false
                                animateY(1500)
                                invalidate()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showShareDialog(name: String) {
        val items = arrayOf("MAL profile", "Anime list", "Manga list")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Share your")
            .setItems(items) { dialog, which ->
                openShareChooser(name, which)
                dialog.dismiss()
            }
            .show()
    }

    private fun openShareChooser(name: String, which: Int) {
        val url = when (which) {
            0 -> "https://myanimelist.net/profile/$name"
            1 -> "https://myanimelist.net/animelist/$name"
            else -> "https://myanimelist.net/mangalist/$name"
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share your MAL link to")
        startActivity(shareIntent)
    }

    private val showSettings = View.OnClickListener {
        navController.navigate(R.id.action_profile_item_to_settingsFragment)
    }

    private var isDetailsCardOpen = false
    private val toggleDetailsCard = View.OnClickListener {
        binding.profileContent.apply {
            if (isDetailsCardOpen) {
                ibCollapseDetails.startAnimation(rotateCloseArrow)
                profileDetailsCardContent.root.isVisible = false
            } else {
                ibCollapseDetails.startAnimation(rotateOpenArrow)
                profileDetailsCardContent.root.isVisible = true
            }
        }
        isDetailsCardOpen = !isDetailsCardOpen
    }

    private val rotateCloseArrow by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateOpenArrow by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}