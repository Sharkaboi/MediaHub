package com.sharkaboi.mediahub.modules.anime_details.ui

import GetNextAiringAnimeEpisodeQuery
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.constants.UIConstants.setMediaHubChipStyle
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.common.util.openUrl
import com.sharkaboi.mediahub.data.api.constants.MALExternalLinks
import com.sharkaboi.mediahub.data.api.enums.AnimeRating.getAnimeRating
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.getAnimeAiringStatus
import com.sharkaboi.mediahub.data.api.enums.getAnimeNsfwRating
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.databinding.CustomEpisodeCountDialogBinding
import com.sharkaboi.mediahub.databinding.FragmentAnimeDetailsBinding
import com.sharkaboi.mediahub.modules.anime_details.adapters.RecommendedAnimeAdapter
import com.sharkaboi.mediahub.modules.anime_details.adapters.RelatedAnimeAdapter
import com.sharkaboi.mediahub.modules.anime_details.adapters.RelatedMangaAdapter
import com.sharkaboi.mediahub.modules.anime_details.util.*
import com.sharkaboi.mediahub.modules.anime_details.vm.AnimeDetailsState
import com.sharkaboi.mediahub.modules.anime_details.vm.AnimeDetailsViewModel
import com.sharkaboi.mediahub.modules.anime_details.vm.NextEpisodeDetailsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailsFragment : Fragment() {
    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val animeDetailsViewModel by viewModels<AnimeDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        binding.swipeRefresh.setOnRefreshListener(handleSwipeRefresh)
    }

    private val handleSwipeRefresh = {
        animeDetailsViewModel.refreshDetails()
        binding.swipeRefresh.isRefreshing = false
    }

    private fun setUpObservers() {
        observe(animeDetailsViewModel.animeDetailState, handleAnimeDetailsStateUpdate)
        observe(animeDetailsViewModel.nextEpisodeDetails, handleNewEpisodeStateUpdate)
        observe(animeDetailsViewModel.animeDetailsUpdate, handleListStatusUpdate)
    }

    private val handleAnimeDetailsStateUpdate = { state: AnimeDetailsState ->
        binding.progressBar.isShowing = state is AnimeDetailsState.Loading
        when (state) {
            is AnimeDetailsState.FetchSuccess -> setData(state.animeByIDResponse)
            is AnimeDetailsState.AnimeDetailsFailure -> showToast(state.message)
            else -> Unit
        }
    }

    private val handleNewEpisodeStateUpdate = { state: NextEpisodeDetailsState ->
        binding.nextEpisodeDetails.nextEpisodeProgress.isVisible =
            state is NextEpisodeDetailsState.Loading
        binding.nextEpisodeDetails.root.isGone =
            state is NextEpisodeDetailsState.NextEpisodeDetailsFailure
        when (state) {
            is NextEpisodeDetailsState.FetchSuccess -> setNextEpisodeData(state.nextAiringEpisode)
            else -> Unit
        }
    }

    private val handleListStatusUpdate = { state: AnimeDetailsUpdateClass ->
        binding.animeDetailsUserListCard.apply {
            btnStatus.text =
                state.animeStatus?.getFormattedString(requireContext())
                ?: getString(R.string.not_added)
            btnScore.text = getString(R.string.media_rating_template, state.score ?: 0)
            btnCount.text = context?.getProgressStringWith(state.numWatchedEpisode, state.totalEps)
            btnScore.setOnClickListener {
                openScoreDialog(state.score)
            }
            btnStatus.setOnClickListener {
                openStatusDialog(state.animeStatus?.name, state.animeId)
            }
            btnCount.setOnClickListener {
                openAnimeWatchedCountDialog(
                    state.totalEps,
                    state.numWatchedEpisode
                )
            }
        }
        Unit
    }

    private fun setNextEpisodeData(nextAiringEpisodeDetails: GetNextAiringAnimeEpisodeQuery.Media) {
        val nextEp = nextAiringEpisodeDetails.nextAiringEpisode
        if (nextEp == null) {
            binding.nextEpisodeDetails.root.isGone = true
        } else {
            binding.nextEpisodeDetails.tvNextEpisodeDetails.text =
                context?.getAiringTimeFormatted(nextEp)
        }
    }

    private fun setData(animeByIDResponse: AnimeByIDResponse) {
        setupAnimeMainDetailLayout(animeByIDResponse)
        setupAnimeUserListStatusCard(animeByIDResponse)
        setupAnimeOtherDetailLayout(animeByIDResponse)
        setupAnimeOtherDetailsCard(animeByIDResponse)
        setupAnimeOtherDetailsButtons(animeByIDResponse)
        setupAnimeRecommendationsList(animeByIDResponse.recommendations)
        setupRelatedAnimeList(animeByIDResponse.relatedAnime)
        setupRelatedMangaList(animeByIDResponse.relatedManga)
    }

    private fun setupAnimeUserListStatusCard(animeByIDResponse: AnimeByIDResponse) =
        binding.animeDetailsUserListCard.apply {
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

    private fun setupRelatedMangaList(relatedManga: List<AnimeByIDResponse.RelatedManga>) {
        val rvRelatedManga = binding.otherDetails.rvRelatedManga
        rvRelatedManga.adapter = RelatedMangaAdapter { mangaId ->
            openMangaWithId(mangaId)
        }.apply {
            submitList(relatedManga)
        }
        binding.otherDetails.tvRelatedMangaHint.isVisible = relatedManga.isNotEmpty()
        rvRelatedManga.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRelatedManga.setHasFixedSize(true)
        rvRelatedManga.itemAnimator = DefaultItemAnimator()
    }

    private fun openMangaWithId(mangaId: Int) {
        val action = BottomNavGraphDirections.openMangaById(mangaId)
        navController.navigate(action)
    }

    private fun setupRelatedAnimeList(relatedAnime: List<AnimeByIDResponse.RelatedAnime>) {
        val rvRelatedAnime = binding.otherDetails.rvRelatedAnime
        rvRelatedAnime.adapter = RelatedAnimeAdapter { animeId ->
            openAnimeWithId(animeId)
        }.apply {
            submitList(relatedAnime)
        }
        binding.otherDetails.tvRelatedAnimeHint.isVisible = relatedAnime.isNotEmpty()
        rvRelatedAnime.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRelatedAnime.setHasFixedSize(true)
        rvRelatedAnime.itemAnimator = DefaultItemAnimator()
    }

    private fun setupAnimeRecommendationsList(recommendations: List<AnimeByIDResponse.Recommendation>) {
        val rvRecommendations = binding.otherDetails.rvRecommendations
        rvRecommendations.adapter = RecommendedAnimeAdapter { animeId ->
            openAnimeWithId(animeId)
        }.apply {
            submitList(recommendations)
        }
        binding.otherDetails.tvRecommendations.isVisible = recommendations.isNotEmpty()
        rvRecommendations.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecommendations.setHasFixedSize(true)
        rvRecommendations.itemAnimator = DefaultItemAnimator()
    }

    private fun openAnimeWithId(animeId: Int) {
        val action = BottomNavGraphDirections.openAnimeById(animeId)
        navController.navigate(action)
    }

    private fun setupAnimeOtherDetailsButtons(animeByIDResponse: AnimeByIDResponse) =
        binding.apply {
            otherDetails.btnBackground.setOnClickListener {
                openBackgroundDialog(animeByIDResponse.background)
            }
            otherDetails.btnCharacters.setOnClickListener {
                openUrl(
                    MALExternalLinks.getAnimeCharactersLink(
                        animeByIDResponse.id,
                        animeByIDResponse.title
                    )
                )
            }
            otherDetails.btnStaff.setOnClickListener {
                openUrl(
                    MALExternalLinks.getAnimeStaffLink(
                        animeByIDResponse.id,
                        animeByIDResponse.title
                    )
                )
            }
            otherDetails.btnReviews.setOnClickListener {
                openUrl(
                    MALExternalLinks.getAnimeReviewsLink(
                        animeByIDResponse.id,
                        animeByIDResponse.title
                    )
                )
            }
            otherDetails.btnNews.setOnClickListener {
                openUrl(
                    MALExternalLinks.getAnimeNewsLink(
                        animeByIDResponse.id,
                        animeByIDResponse.title
                    )
                )
            }
            otherDetails.btnVideos.setOnClickListener {
                openUrl(
                    MALExternalLinks.getAnimeVideosLink(
                        animeByIDResponse.id,
                        animeByIDResponse.title
                    )
                )
            }
            otherDetails.btnStatistics.setOnClickListener {
                openStatsDialog(animeByIDResponse.statistics)
            }
        }

    private fun setupAnimeOtherDetailsCard(animeByIDResponse: AnimeByIDResponse) = binding.apply {
        otherDetails.btnMediaType.text =
            context?.getMediaTypeStringWith(animeByIDResponse.mediaType.capitalizeFirst())
        otherDetails.btnMediaType.setOnClickListener {
            openAnimeRankingWith(animeByIDResponse.mediaType)
        }
        otherDetails.btnAnimeCurrentStatus.text =
            context?.getAnimeAiringStatus(animeByIDResponse.status)
        otherDetails.btnTotalEps.text =
            context?.getEpisodesOfAnimeString(animeByIDResponse.numEpisodes)
        otherDetails.btnSeason.text = context?.getAnimeSeasonString(
            animeByIDResponse.startSeason?.season,
            animeByIDResponse.startSeason?.year
        )
        otherDetails.btnSeason.setOnClickListener {
            openAnimeSeasonalWith(
                animeByIDResponse.startSeason?.season,
                animeByIDResponse.startSeason?.year ?: 0
            )
        }
        otherDetails.tvSchedule.text =
            context?.getAnimeBroadcastTime(animeByIDResponse.broadcast)
        otherDetails.btnSource.text =
            context?.getAnimeOriginalSourceString(animeByIDResponse.source)
        otherDetails.btnAverageLength.text =
            context?.getEpisodeLengthFromSeconds(animeByIDResponse.averageEpisodeDuration)
        otherDetails.ibNotify.setOnClickListener {
            onNotifyClick(animeByIDResponse.id, animeByIDResponse.broadcast)
        }
        otherDetails.chipGroupOptions.forEach {
            if (it is Chip) {
                it.setMediaHubChipStyle()
            }
        }
    }

    private fun openAnimeSeasonalWith(season: String?, year: Int) {
        val action =
            AnimeDetailsFragmentDirections.openAnimeSeasonals(season, year)
        navController.navigate(action)
    }

    private fun openAnimeRankingWith(mediaType: String) {
        val action =
            AnimeDetailsFragmentDirections.openAnimeRankings(mediaType)
        navController.navigate(action)
    }

    private fun setupAnimeOtherDetailLayout(animeByIDResponse: AnimeByIDResponse) {
        binding.otherDetails.tvSynopsis.text =
            animeByIDResponse.synopsis.ifNullOrBlank { getString(R.string.n_a) }
        binding.otherDetails.tvSynopsis.setOnClickListener {
            showFullSynopsisDialog(animeByIDResponse.synopsis)
        }
        setupRatingsChipGroup(animeByIDResponse)
        setupGenresChipGroup(animeByIDResponse.genres)
    }

    private fun setupGenresChipGroup(genres: List<AnimeByIDResponse.Genre>?) {
        val chipGroup = binding.otherDetails.genresChipGroup
        chipGroup.removeAllViews()
        if (genres.isNullOrEmpty()) {
            val naChip = Chip(context)
            naChip.setMediaHubChipStyle()
            naChip.text = getString(R.string.n_a)
            chipGroup.addView(naChip)
        } else {
            genres.forEach { genre ->
                val genreChip = Chip(context)
                genreChip.setMediaHubChipStyle()
                genreChip.text = genre.name
                genreChip.setOnClickListener {
                    openUrl(MALExternalLinks.getAnimeGenresLink(genre))
                }
                chipGroup.addView(genreChip)
            }
        }
    }

    private fun setupAnimeMainDetailLayout(animeByIDResponse: AnimeByIDResponse) = binding.apply {
        setupAnimeImagePreview(animeByIDResponse)
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
        setupStudiosChipGroup(animeByIDResponse.studios)
    }

    private fun setupRatingsChipGroup(animeByIDResponse: AnimeByIDResponse) {
        val chipGroup = binding.otherDetails.ratingsChipGroup
        chipGroup.removeAllViews()
        val nsfwRatingChip = Chip(context)
        nsfwRatingChip.setMediaHubChipStyle()
        nsfwRatingChip.text = nsfwRatingChip.context.getAnimeNsfwRating(animeByIDResponse.nsfw)
        chipGroup.addView(nsfwRatingChip)
        val pgRatingChip = Chip(context)
        pgRatingChip.setMediaHubChipStyle()
        pgRatingChip.text = pgRatingChip.context.getAnimeRating(animeByIDResponse.rating)
        chipGroup.addView(pgRatingChip)
    }

    private fun setupAnimeImagePreview(animeByIDResponse: AnimeByIDResponse) {
        binding.ivAnimeMainPicture.load(
            uri = animeByIDResponse.mainPicture?.large ?: animeByIDResponse.mainPicture?.medium,
            builder = UIConstants.AnimeImageBuilder
        )
        binding.ivAnimeMainPicture.setOnClickListener {
            openImagesViewPager(animeByIDResponse.pictures)
        }
    }

    private fun setupStudiosChipGroup(studios: List<AnimeByIDResponse.Studio>) {
        binding.studiosChipGroup.removeAllViews()
        if (studios.isEmpty()) {
            val textView = TextView(context)
            textView.text = getString(R.string.n_a)
            binding.studiosChipGroup.addView(textView)
        } else {
            studios.forEach { studio ->
                val textView = TextView(context)
                textView.setTextColor(
                    ContextCompat.getColor(textView.context, R.color.colorPrimary)
                )
                textView.setTypeface(null, Typeface.BOLD)
                textView.text = studio.name.plus(" ")
                textView.setOnClickListener {
                    openUrl(MALExternalLinks.getAnimeProducerPageLink(studio))
                }
                binding.studiosChipGroup.addView(textView)
            }
        }
    }

    private fun onNotifyClick(id: Int, broadcast: AnimeByIDResponse.Broadcast?) {
        showToast(R.string.coming_soon_hint)
    }

    private fun openImagesViewPager(pictures: List<AnimeByIDResponse.Picture>) {
        val images: List<String> = pictures.map { it.large ?: it.medium }
        val action = BottomNavGraphDirections.openImageSlider(images.toTypedArray())
        navController.navigate(action)
    }

    private fun showFullSynopsisDialog(synopsis: String?) =
        requireContext().showNoActionOkDialog(R.string.synopsis, synopsis)

    private fun openStatusDialog(status: String?, animeId: Int) {
        val singleItems = arrayOf(getString(R.string.not_added)) + AnimeStatus.malStatuses.map {
            it.getFormattedString(requireContext())
        }
        val checkedItem =
            status?.let { AnimeStatus.malStatuses.indexOfFirst { it.name == status } + 1 } ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.media_set_status_hint)
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
            .setTitle(R.string.media_set_score_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when (which) {
                    checkedItem -> Unit
                    else -> animeDetailsViewModel.setScore(which)
                }
                dialog.dismiss()
            }.show()
    }

    private fun openAnimeWatchedCountDialog(totalEps: Int?, watchedEps: Int?) {
        if (totalEps == null || totalEps == 0) {
            showWatchedCountDialogWithTextField(watchedEps)
        } else {
            showWatchedCountDialogWithList(totalEps, watchedEps)
        }
    }

    private fun showWatchedCountDialogWithList(totalEps: Int, watchedEps: Int?) {
        val singleItems = (0..totalEps).map { it.toString() }.toTypedArray()
        val checkedItem = watchedEps ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.anime_watched_till_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                animeDetailsViewModel.setEpisodeCount(which)
                dialog.dismiss()
            }
            .show()
    }

    @SuppressLint("InflateParams")
    private fun showWatchedCountDialogWithTextField(watchedEps: Int?) {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.custom_episode_count_dialog, null)
        val binding: CustomEpisodeCountDialogBinding =
            CustomEpisodeCountDialogBinding.bind(view)
        binding.etNum.setText(watchedEps?.toString() ?: String.emptyString)
        MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                val count = binding.etNum.text?.toString()?.toInt() ?: 0
                animeDetailsViewModel.setEpisodeCount(count)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openBackgroundDialog(background: String?) =
        requireContext().showNoActionOkDialog(R.string.background, background)

    private fun openStatsDialog(statistics: AnimeByIDResponse.Statistics?) =
        requireContext().showNoActionOkDialog(
            R.string.statistics,
            context?.getAnimeStats(statistics)
        )

    private fun showAlternateTitlesDialog(alternativeTitles: AnimeByIDResponse.AlternativeTitles?) =
        requireContext().showNoActionOkDialog(
            R.string.alternate_titles_hint,
            context?.getFormattedAnimeTitlesString(alternativeTitles)
        )
}
