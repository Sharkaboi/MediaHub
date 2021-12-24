package com.sharkaboi.mediahub.modules.profile.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.chip.Chip
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.common.util.openShareChooser
import com.sharkaboi.mediahub.common.util.openUrl
import com.sharkaboi.mediahub.data.api.constants.MALExternalLinks
import com.sharkaboi.mediahub.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.databinding.FragmentProfileBinding
import com.sharkaboi.mediahub.modules.profile.util.MPAndroidChartValueFormatter
import com.sharkaboi.mediahub.modules.profile.util.getDaysCountString
import com.sharkaboi.mediahub.modules.profile.util.getEpisodesOfAnimeFullString
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
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            profileContent.ivProfileImage.load(
                drawableResId = R.drawable.ic_profile_placeholder,
                builder = UIConstants.ProfileImageBuilder
            )
            profileContent.chipGroupOptions.forEach {
                if (it is Chip) {
                    it.setMediaHubChipStyle()
                }
            }
            profileContent.ibSettings.setOnClickListener(showSettings)
        }
    }

    private fun setObservers() {
        observe(profileViewModel.uiState) { uiState ->
            binding.progressBar.isShowing = uiState is ProfileStates.Loading
            when (uiState) {
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
        setUpBannerSection(userDetailsResponse)
        setUserDetailsSection(userDetailsResponse)
        setUserStatsSection(userDetailsResponse)
        setOtherDetailsSection(userDetailsResponse.name)
        binding.profileContent.ibCollapseDetails.setOnClickListener(toggleDetailsCard)
        toggleDetailsCard.onClick(null)
    }

    private fun setOtherDetailsSection(name: String) = binding.profileContent.apply {
        btnBlogs.setOnClickListener {
            openUrl(MALExternalLinks.getBlogsLink(name))
        }
        btnClubs.setOnClickListener {
            openUrl(MALExternalLinks.getClubsLink(name))
        }
        btnForumTopics.setOnClickListener {
            openUrl(MALExternalLinks.getForumTopicsLink(name))
        }
        btnFriends.setOnClickListener {
            openUrl(MALExternalLinks.getFriendsLink(name))
        }
        btnHistory.setOnClickListener {
            openUrl(MALExternalLinks.getHistoryLink(name))
        }
        btnRecommendations.setOnClickListener {
            openUrl(MALExternalLinks.getRecommendationsLink(name))
        }
        btnReviews.setOnClickListener {
            openUrl(MALExternalLinks.getReviewsLink(name))
        }
    }

    private fun setUserStatsSection(userDetailsResponse: UserDetailsResponse) {
        if (userDetailsResponse.animeStatistics == null) {
            binding.profileContent.tvStatsEmptyHint.isVisible = true
        } else {
            binding.profileContent.profileStatsContent.root.isVisible = true
            binding.profileContent.profileStatsContent.apply {
                tvEpisodes.text =
                    context?.getEpisodesOfAnimeFullString(userDetailsResponse.animeStatistics.numEpisodes)
                tvDaysWatched.text =
                    context?.getDaysCountString(userDetailsResponse.animeStatistics.numDaysCompleted.toLong())
                tvReWatchCount.text = context?.resources?.getQuantityString(
                    R.plurals.re_watch_times,
                    userDetailsResponse.animeStatistics.numTimesReWatched.toInt(),
                    userDetailsResponse.animeStatistics.numTimesReWatched.toLong()
                )
                tvMeanScore.text =
                    userDetailsResponse.animeStatistics.meanScore.toString()
                setupPieChart(userDetailsResponse.animeStatistics)
            }
        }
    }

    private fun setupPieChart(animeStatistics: UserDetailsResponse.AnimeStatistics) {
        val pieChart = binding.profileContent.profileStatsContent.pieItemCounts
        val entries = listOf(
            PieEntry(
                animeStatistics.numItemsCompleted.toFloat(),
                getString(R.string.anime_status_completed)
            ),
            PieEntry(
                animeStatistics.numItemsOnHold.toFloat(),
                getString(R.string.anime_status_on_hold)
            ),
            PieEntry(
                animeStatistics.numItemsDropped.toFloat(),
                getString(R.string.anime_status_dropped)
            ),
            PieEntry(
                animeStatistics.numItemsWatching.toFloat(),
                getString(R.string.anime_status_watching)
            ),
            PieEntry(
                animeStatistics.numItemsPlanToWatch.toFloat(),
                getString(R.string.anime_status_planned)
            )
        )
        if (entries.count { it.value == 0f } != entries.count()) {
            val pieDataSet = PieDataSet(entries, String.emptyString)
            pieDataSet.colors = listOf(
                "#2ecc71".parseRGB(),
                "#ffa500".parseRGB(),
                "#e74c3c".parseRGB(),
                "#3498db".parseRGB(),
                "#5634eb".parseRGB(),
            )
            pieDataSet.valueTextSize = 14f
            pieDataSet.valueTextColor = Color.WHITE
            pieDataSet.valueFormatter = MPAndroidChartValueFormatter()
            pieChart.data = PieData(pieDataSet)
            pieChart.setTouchEnabled(false)
            pieChart.setDrawEntryLabels(false)
            pieChart.setNoDataTextColor("#ba68c8".parseRGB())
            pieChart.legend.textColor = MaterialColors.getColor(pieChart, R.attr.colorOnSurface)
            pieChart.setHoleColor(Color.TRANSPARENT)
            pieChart.description.text = String.emptyString
            pieChart.description.isEnabled = false
            pieChart.animateY(1500)
            pieChart.invalidate()
        }
    }

    private fun setUserDetailsSection(userDetailsResponse: UserDetailsResponse) =
        binding.profileContent.profileDetailsCardContent.apply {
            tvBirthDay.text =
                userDetailsResponse.birthday?.tryParseDateTime()?.formatDateDMY()
                    ?: getString(R.string.n_a)
            tvGender.text =
                userDetailsResponse.gender?.capitalizeFirst()
                    ?: getString(R.string.n_a)
            tvJoinedAt.text =
                userDetailsResponse.joinedAt.tryParseDateTime()?.formatDateDMY()
                    ?: getString(R.string.n_a)
            tvLocation.text =
                userDetailsResponse.location.ifNullOrBlank { getString(R.string.n_a) }
            tvTimeZone.text = userDetailsResponse.timeZone ?: getString(R.string.n_a)
            tvSupporter.text =
                if (userDetailsResponse.isSupporter == null || !userDetailsResponse.isSupporter) {
                    getString(R.string.no)
                } else {
                    getString(R.string.yes)
                }
        }

    private fun setUpBannerSection(userDetailsResponse: UserDetailsResponse) =
        binding.profileContent.apply {
            ivProfileImage.load(
                uri = userDetailsResponse.profilePicUrl,
                builder = UIConstants.ProfileImageBuilder
            )
            ivProfileImage.setOnClickListener {
                val action =
                    BottomNavGraphDirections.openImageSlider(arrayOf(userDetailsResponse.profilePicUrl))
                navController.navigate(action)
            }
            tvName.text = userDetailsResponse.name
            ibShare.setOnClickListener {
                showShareDialog(userDetailsResponse.name)
            }
        }

    private fun showShareDialog(name: String) {
        val items = arrayOf(
            getString(R.string.share_mal_profile),
            getString(R.string.share_anime_list),
            getString(R.string.share_manga_list)
        )
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.share_your_dialog_hint)
            .setItems(items) { dialog, which ->
                onShareClick(name, which)
                dialog.dismiss()
            }.show()
    }

    private fun onShareClick(name: String, which: Int) {
        val url = when (which) {
            0 -> MALExternalLinks.getProfileLink(name)
            1 -> MALExternalLinks.getUserAnimeListLink(name)
            else -> MALExternalLinks.getUserMangaListLink(name)
        }
        openShareChooser(url, getString(R.string.share_your_hint))
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
        AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim)
    }
    private val rotateOpenArrow by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim)
    }
}
