package com.sharkaboi.mediahub.modules.anime_details.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.ShapeAppearanceModel
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.databinding.CustomEpisodeCountDialogBinding
import com.sharkaboi.mediahub.databinding.FragmentAnimeDetailsBinding
import com.sharkaboi.mediahub.modules.anime_details.adapters.RecommendedAnimeAdapter
import com.sharkaboi.mediahub.modules.anime_details.adapters.RelatedAnimeAdapter
import com.sharkaboi.mediahub.modules.anime_details.adapters.RelatedMangaAdapter
import com.sharkaboi.mediahub.modules.anime_details.vm.AnimeDetailsState
import com.sharkaboi.mediahub.modules.anime_details.vm.AnimeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AnimeDetailsFragment : Fragment() {
    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val args: AnimeDetailsFragmentArgs by navArgs()
    private val animeDetailsViewModel by viewModels<AnimeDetailsViewModel>()

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
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        setUpObservers()
    }

    private fun setUpObservers() {
        animeDetailsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.progressBar.isShowing = uiState is AnimeDetailsState.Loading
            when (uiState) {
                is AnimeDetailsState.Idle -> animeDetailsViewModel.getAnimeDetails(args.animeId)
                is AnimeDetailsState.FetchSuccess -> setData(uiState.animeByIDResponse)
                is AnimeDetailsState.AnimeDetailsFailure -> showToast(uiState.message)
                else -> Unit
            }
        }
        animeDetailsViewModel.animeDetailsUpdate.observe(viewLifecycleOwner) { animeDetails ->
            binding.animeDetailsUserListCard.apply {
                btnStatus.text =
                    animeDetails.animeStatus?.getFormattedString()
                        ?: getString(R.string.not_added)
                btnScore.text = ("${animeDetails.score ?: 0}/10")
                btnCount.text = ("${animeDetails.numWatchedEpisode ?: 0}/${
                    if (animeDetails.totalEps == 0)
                        "??"
                    else
                        animeDetails.totalEps.toString()
                }")
                btnScore.setOnClickListener {
                    openScoreDialog(animeDetails.score)
                }
                btnStatus.setOnClickListener {
                    openStatusDialog(animeDetails.animeStatus?.name, animeDetails.animeId)
                }
                btnCount.setOnClickListener {
                    openAnimeWatchedCountDialog(
                        animeDetails.totalEps,
                        animeDetails.numWatchedEpisode
                    )
                }
            }
        }
    }

    private fun setData(animeByIDResponse: AnimeByIDResponse) {
        binding.apply {
            tvAnimeName.text = animeByIDResponse.title
            tvAlternateTitles.setOnClickListener {
                showAlternateTitlesDialog(animeByIDResponse.alternativeTitles)
            }
            tvStartDate.text =
                animeByIDResponse.startDate?.tryParseDate()?.formatDateDMY()
                    ?: getString(R.string.n_a)
            tvEndDate.text =
                animeByIDResponse.endDate?.tryParseDate()?.formatDateDMY()
                    ?: getString(R.string.n_a)
            tvMeanScore.text = animeByIDResponse.mean?.toString() ?: getString(R.string.n_a)
            tvRank.text = animeByIDResponse.rank?.toString() ?: getString(R.string.n_a)
            tvPopularityRank.text = animeByIDResponse.popularity.toString()
            tvStudios.text = animeByIDResponse.studios.joinToString { it.name }
            ivAnimeMainPicture.load(
                animeByIDResponse.mainPicture?.large ?: animeByIDResponse.mainPicture?.medium
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_anime_placeholder)
                error(R.drawable.ic_anime_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            ivAnimeMainPicture.setOnClickListener {
                openImagesViewPager(animeByIDResponse.pictures)
            }
            otherDetails.tvSynopsis.text = animeByIDResponse.synopsis ?: getString(R.string.n_a)
            otherDetails.tvSynopsis.setOnClickListener {
                showFullSynopsisDialog(animeByIDResponse.synopsis ?: getString(R.string.n_a))
            }
            otherDetails.ratingsChipGroup.apply {
                removeAllViews()
                addView(Chip(context).apply {
                    setEnsureMinTouchTargetSize(false)
                    shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
                    text = animeByIDResponse.nsfw?.getNsfwRating() ?: getString(R.string.n_a)
                })
                addView(Chip(context).apply {
                    setEnsureMinTouchTargetSize(false)
                    shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
                    text = animeByIDResponse.rating?.getRating() ?: getString(R.string.n_a)
                })
            }
            animeByIDResponse.genres.let {
                otherDetails.genresChipGroup.removeAllViews()
                if (it.isEmpty()) {
                    otherDetails.genresChipGroup.addView(Chip(context).apply {
                        setEnsureMinTouchTargetSize(false)
                        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
                        text = getString(R.string.n_a)
                    })
                } else {
                    it.forEach { genre ->
                        otherDetails.genresChipGroup.addView(Chip(context).apply {
                            setEnsureMinTouchTargetSize(false)
                            shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
                            text = genre.name
                        })
                    }
                }
            }
            otherDetails.tvMediaType.text = animeByIDResponse.mediaType.uppercase(Locale.ROOT)
            otherDetails.tvAnimeCurrentStatus.text = animeByIDResponse.status.getStatus()
            otherDetails.tvTotalEps.text =
                animeByIDResponse.numEpisodes.let {
                    if (it == 0)
                        getString(R.string.n_a)
                    else
                        it.toString()
                }
            otherDetails.tvSeason.text = animeByIDResponse.startSeason?.let {
                "${it.season.capitalizeFirst()} ${it.year}"
            } ?: getString(R.string.n_a)
            otherDetails.tvSchedule.text =
                animeByIDResponse.broadcast?.getBroadcastTime() ?: getString(R.string.n_a)
            otherDetails.tvSource.text =
                animeByIDResponse.source?.replace('_', ' ')?.capitalizeFirst()
                    ?: getString(R.string.n_a)
            otherDetails.tvAverageLength.text =
                animeByIDResponse.averageEpisodeDuration?.getEpisodeLengthFromSeconds()
                    ?: getString(R.string.n_a)
            otherDetails.tvBackground.setOnClickListener {
                openBackgroundDialog(animeByIDResponse.background)
            }

            otherDetails.tvStatistics.setOnClickListener {
                openStatsDialog(animeByIDResponse.statistics)
            }
            otherDetails.rvRecommendations.apply {
                adapter = RecommendedAnimeAdapter { animeId ->
                    val action = AnimeDetailsFragmentDirections.animeDetailsWithId(animeId)
                    findNavController().navigate(action)
                }.apply {
                    submitList(animeByIDResponse.recommendations)
                }
                otherDetails.tvRecommendations.isVisible =
                    animeByIDResponse.recommendations.isNotEmpty()
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
            }
            otherDetails.rvRelatedAnime.apply {
                adapter = RelatedAnimeAdapter { animeId ->
                    val action = AnimeDetailsFragmentDirections.animeDetailsWithId(animeId)
                    findNavController().navigate(action)
                }.apply {
                    submitList(animeByIDResponse.relatedAnime)
                }
                otherDetails.tvRelatedAnimeHint.isVisible =
                    animeByIDResponse.relatedAnime.isNotEmpty()
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
            }
            otherDetails.rvRelatedManga.apply {
                adapter = RelatedMangaAdapter { mangaId ->
                    // FIXME: 26-04-2021 Add call when finished with manga details
                    showToast("$mangaId clicked")
                }.apply {
                    submitList(animeByIDResponse.relatedManga)
                }
                otherDetails.tvRelatedMangaHint.isVisible =
                    animeByIDResponse.relatedManga.isNotEmpty()
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
            }
            animeDetailsUserListCard.apply {
                btnPlus1.setOnClickListener {
                    animeDetailsViewModel.add1ToWatchedEps()
                }
                btnPlus5.setOnClickListener {
                    animeDetailsViewModel.add5ToWatchedEps()
                }
                btnPlus10.setOnClickListener {
                    animeDetailsViewModel.add10ToWatchedEps()
                }
                btnCount.setOnClickListener {
                    openAnimeWatchedCountDialog(
                        animeByIDResponse.numEpisodes,
                        animeByIDResponse.myListStatus?.numEpisodesWatched
                    )
                }
                btnScore.setOnClickListener {
                    openScoreDialog(animeByIDResponse.myListStatus?.score)
                }
                btnStatus.setOnClickListener {
                    openStatusDialog(animeByIDResponse.myListStatus?.status, animeByIDResponse.id)
                }
                btnConfirm.setOnClickListener {
                    animeDetailsViewModel.submitStatusUpdate(animeByIDResponse.id)
                }
            }
        }
    }

    private fun openImagesViewPager(pictures: List<AnimeByIDResponse.Picture>) {
        val images: List<String> = pictures.map { it.large ?: it.medium }
        val action = AnimeDetailsFragmentDirections.openImages(images.toTypedArray())
        navController.navigate(action)
    }

    private fun showFullSynopsisDialog(synopsis: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Synopsis")
            .setMessage(
                synopsis
            ).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun openStatusDialog(status: String?, animeId: Int) {
        val singleItems =
            arrayOf("Not added") + AnimeStatus.malStatuses.map { it.getFormattedString() }
        val checkedItem =
            status?.let { AnimeStatus.malStatuses.indexOfFirst { it.name == status } + 1 } ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Set status as")
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when (which) {
                    checkedItem -> Unit
                    0 -> animeDetailsViewModel.removeFromList(animeId)
                    else -> animeDetailsViewModel.setStatus(AnimeStatus.malStatuses[which - 1])
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun openScoreDialog(score: Int?) {
        val singleItems = (0..10).map { it.toString() }.toTypedArray()
        val checkedItem = score ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Set score as")
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when (which) {
                    checkedItem -> Unit
                    else -> animeDetailsViewModel.setScore(which)
                }
                dialog.dismiss()
            }
            .show()
    }

    @SuppressLint("InflateParams")
    private fun openAnimeWatchedCountDialog(totalEps: Int?, watchedEps: Int?) {
        if (totalEps == null || totalEps == 0) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.custom_episode_count_dialog, null)
            val binding: CustomEpisodeCountDialogBinding =
                CustomEpisodeCountDialogBinding.bind(view)
            binding.etNum.setText(watchedEps?.toString() ?: "")
            MaterialAlertDialogBuilder(requireContext())
                .setView(binding.root)
                .setPositiveButton("Ok") { dialog, _ ->
                    val count = binding.etNum.text?.toString()?.toInt() ?: 0
                    animeDetailsViewModel.setEpisodeCount(count)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            val singleItems = (0..totalEps).map { it.toString() }.toTypedArray()
            val checkedItem = watchedEps ?: 0
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Watched till episode")
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    animeDetailsViewModel.setEpisodeCount(which)
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun openBackgroundDialog(background: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Background")
            .setMessage(
                if (background != null && background.isNotBlank())
                    background
                else
                    getString(R.string.n_a)
            ).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun openStatsDialog(statistics: AnimeByIDResponse.Statistics?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Statistics")
            .setMessage(
                statistics?.getStats() ?: getString(R.string.n_a)
            ).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showAlternateTitlesDialog(alternativeTitles: AnimeByIDResponse.AlternativeTitles?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alternate titles")
            .setMessage(
                alternativeTitles?.getFormattedString() ?: getString(R.string.n_a)
            ).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}

