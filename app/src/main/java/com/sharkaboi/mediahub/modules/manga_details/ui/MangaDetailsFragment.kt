package com.sharkaboi.mediahub.modules.manga_details.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
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
import com.sharkaboi.mediahub.common.util.openShareChooser
import com.sharkaboi.mediahub.common.util.openUrl
import com.sharkaboi.mediahub.data.api.constants.MALExternalLinks
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.getMangaNsfwRating
import com.sharkaboi.mediahub.data.api.enums.getMangaPublishStatus
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.databinding.CustomReadCountDialogBinding
import com.sharkaboi.mediahub.databinding.FragmentMangaDetailsBinding
import com.sharkaboi.mediahub.modules.manga_details.adapters.RecommendedMangaAdapter
import com.sharkaboi.mediahub.modules.manga_details.adapters.RelatedAnimeAdapter
import com.sharkaboi.mediahub.modules.manga_details.adapters.RelatedMangaAdapter
import com.sharkaboi.mediahub.modules.manga_details.util.*
import com.sharkaboi.mediahub.modules.manga_details.vm.MangaDetailsState
import com.sharkaboi.mediahub.modules.manga_details.vm.MangaDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MangaDetailsFragment : Fragment() {
    private var _binding: FragmentMangaDetailsBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val mangaDetailsViewModel by viewModels<MangaDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaDetailsBinding.inflate(inflater, container, false)
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
        mangaDetailsViewModel.refreshDetails()
        binding.swipeRefresh.isRefreshing = false
    }

    private fun setUpObservers() {
        observe(mangaDetailsViewModel.uiState, handleMangaDetailsUpdate)
        observe(mangaDetailsViewModel.mangaDetailsUpdate, handleListStatusUpdate)
    }

    private val handleMangaDetailsUpdate = { state: MangaDetailsState ->
        binding.progressBar.isShowing = state is MangaDetailsState.Loading
        when (state) {
            is MangaDetailsState.FetchSuccess -> setData(state.mangaByIDResponse)
            is MangaDetailsState.MangaDetailsFailure -> showToast(state.message)
            else -> Unit
        }
    }

    private val handleListStatusUpdate = { state: MangaDetailsUpdateClass ->
        binding.mangaDetailsUserListCard.apply {
            btnStatus.text =
                state.mangaStatus?.getFormattedString(requireContext())
                    ?: getString(R.string.not_added)
            btnScore.text = getString(R.string.media_rating_template, state.score ?: 0)
            btnCountVolumes.text =
                context?.getProgressStringWith(state.numReadVolumes, state.totalVolumes)
            btnCountChaps.text =
                context?.getProgressStringWith(state.numReadChapters, state.totalChapters)
            btnScore.setOnClickListener {
                openScoreDialog(state.score)
            }
            btnStatus.setOnClickListener {
                openStatusDialog(state.mangaStatus?.name)
            }
            btnCountVolumes.setOnClickListener {
                openMangaVolumeCountDialog(
                    state.totalVolumes,
                    state.numReadVolumes
                )
            }
            btnCountChaps.setOnClickListener {
                openMangaChapterCountDialog(
                    state.totalChapters,
                    state.numReadChapters
                )
            }
        }
        Unit
    }

    private fun setData(mangaByIDResponse: MangaByIDResponse) {
        setupMangaMainDetailLayout(mangaByIDResponse)
        setupMangaUserListStatusCard(mangaByIDResponse)
        setupMangaOtherDetailLayout(mangaByIDResponse)
        setupMangaOtherDetailsCard(mangaByIDResponse)
        setupMangaOtherDetailsButtons(mangaByIDResponse)
        setupMangaRecommendationsList(mangaByIDResponse.recommendations)
        setupRelatedMangaList(mangaByIDResponse.relatedManga)
        setupRelatedAnimeList(mangaByIDResponse.relatedAnime)
    }

    private fun setupRelatedMangaList(relatedManga: List<MangaByIDResponse.RelatedManga>) {
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

    private fun setupRelatedAnimeList(relatedAnime: List<MangaByIDResponse.RelatedAnime>) {
        val rvRelatedAnime = binding.otherDetails.rvRelatedAnime
        rvRelatedAnime.adapter = RelatedAnimeAdapter { animeId ->
            openAnimeById(animeId)
        }.apply {
            submitList(relatedAnime)
        }
        binding.otherDetails.tvRelatedAnimeHint.isVisible = relatedAnime.isNotEmpty()
        rvRelatedAnime.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRelatedAnime.setHasFixedSize(true)
        rvRelatedAnime.itemAnimator = DefaultItemAnimator()
    }

    private fun openAnimeById(animeId: Int) {
        val action = BottomNavGraphDirections.openAnimeById(animeId)
        navController.navigate(action)
    }

    private fun setupMangaRecommendationsList(recommendations: List<MangaByIDResponse.Recommendation>) {
        val rvRecommendations = binding.otherDetails.rvRecommendations
        rvRecommendations.adapter = RecommendedMangaAdapter { mangaId ->
            openMangaWithId(mangaId)
        }.apply {
            submitList(recommendations)
        }
        binding.otherDetails.tvRecommendations.isVisible = recommendations.isNotEmpty()
        rvRecommendations.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecommendations.setHasFixedSize(true)
        rvRecommendations.itemAnimator = DefaultItemAnimator()
    }

    private fun openMangaWithId(mangaId: Int) {
        val action = BottomNavGraphDirections.openMangaById(mangaId)
        navController.navigate(action)
    }

    private fun setupMangaOtherDetailsButtons(mangaByIDResponse: MangaByIDResponse) =
        binding.otherDetails.apply {
            btnBackground.setOnClickListener {
                openBackgroundDialog(mangaByIDResponse.background)
            }
            btnCharacters.setOnClickListener {
                openUrl(
                    MALExternalLinks.getMangaCharactersLink(
                        mangaByIDResponse.id,
                        mangaByIDResponse.title
                    )
                )
            }
            btnReviews.setOnClickListener {
                openUrl(
                    MALExternalLinks.getMangaReviewsLink(
                        mangaByIDResponse.id,
                        mangaByIDResponse.title
                    )
                )
            }
            btnNews.setOnClickListener {
                openUrl(
                    MALExternalLinks.getMangaNewsLink(
                        mangaByIDResponse.id,
                        mangaByIDResponse.title
                    )
                )
            }
            btnStatistics.setOnClickListener {
                openStatsDialog(mangaByIDResponse.numListUsers, mangaByIDResponse.numScoringUsers)
            }
        }

    private fun setupMangaOtherDetailsCard(mangaByIDResponse: MangaByIDResponse) =
        binding.otherDetails.apply {
            btnMediaType.text = context?.getMediaTypeStringWith(
                mangaByIDResponse.mediaType.replaceUnderScoreWithWhiteSpace().capitalizeFirst()
            )
            btnMediaType.setOnClickListener {
                openMangaRankingWith(mangaByIDResponse.mediaType)
            }
            btnMangaCurrentStatus.text =
                context?.getMangaPublishStatus(mangaByIDResponse.status)
            btnTotalVols.text = context?.getVolumesOfMangaString(mangaByIDResponse.numVolumes)
            btnTotalChaps.text = context?.getChaptersOfMangaString(mangaByIDResponse.numChapters)
            setupSerializationsChipGroup(mangaByIDResponse.serialization)
            chipGroupOptions.forEach {
                if (it is Chip) {
                    it.setMediaHubChipStyle()
                }
            }
        }

    private fun setupSerializationsChipGroup(serialization: List<MangaByIDResponse.Serialization>) {
        val chipGroup = binding.otherDetails.serializationChipGroup
        chipGroup.removeAllViews()
        if (serialization.isEmpty()) {
            val textView = TextView(context)
            textView.text = getString(R.string.n_a)
            chipGroup.addView(textView)
        } else {
            serialization.forEach { magazine ->
                val textView = TextView(context)
                textView.setTextColor(
                    ContextCompat.getColor(textView.context, R.color.colorPrimary)
                )
                textView.setTypeface(null, Typeface.BOLD)
                textView.text = magazine.node.name.plus(" ")
                textView.setOnClickListener {
                    openUrl(
                        MALExternalLinks.getMangaSerializationPageLink(magazine)
                    )
                }
                chipGroup.addView(textView)
            }
        }
    }

    private fun openMangaRankingWith(mediaType: String) {
        val action =
            MangaDetailsFragmentDirections.openMangaRankings(mediaType)
        navController.navigate(action)
    }

    private fun setupMangaOtherDetailLayout(mangaByIDResponse: MangaByIDResponse) = binding.apply {
        otherDetails.tvSynopsis.text =
            mangaByIDResponse.synopsis.ifNullOrBlank { getString(R.string.n_a) }
        otherDetails.tvSynopsis.setOnClickListener {
            showFullSynopsisDialog(
                mangaByIDResponse.synopsis.ifNullOrBlank { getString(R.string.n_a) }
            )
        }
        otherDetails.tvNsfwRating.text = context?.getMangaNsfwRating(mangaByIDResponse.nsfw)
        setupGenresChipGroup(mangaByIDResponse.genres)
    }

    private fun setupGenresChipGroup(genres: List<MangaByIDResponse.Genre>?) {
        val chipGroup = binding.otherDetails.genresChipGroup
        chipGroup.removeAllViews()
        if (genres.isNullOrEmpty()) {
            val naChip = Chip(context)
            naChip.setMediaHubChipStyle()
            naChip.text = getString(R.string.n_a)
            chipGroup.addView(naChip)
        } else {
            genres.forEach { genre ->
                val chip = Chip(context)
                chip.setMediaHubChipStyle()
                chip.text = genre.name
                chip.setOnClickListener {
                    openUrl(MALExternalLinks.getMangaGenresLink(genre))
                }
                chipGroup.addView(chip)
            }
        }
    }

    private fun setupMangaUserListStatusCard(mangaByIDResponse: MangaByIDResponse) =
        binding.mangaDetailsUserListCard.apply {
            btnScore.setOnClickListener {
                openScoreDialog(mangaByIDResponse.myListStatus?.score)
            }
            btnStatus.setOnClickListener {
                openStatusDialog(mangaByIDResponse.myListStatus?.status)
            }
            btnCountVolumes.setOnClickListener {
                openMangaVolumeCountDialog(
                    mangaByIDResponse.numVolumes,
                    mangaByIDResponse.myListStatus?.numVolumesRead
                )
            }
            btnCountChaps.setOnClickListener {
                openMangaChapterCountDialog(
                    mangaByIDResponse.numChapters,
                    mangaByIDResponse.myListStatus?.numChaptersRead
                )
            }
            btnConfirm.setOnClickListener {
                mangaDetailsViewModel.submitStatusUpdate()
            }
        }

    private fun setupMangaMainDetailLayout(mangaByIDResponse: MangaByIDResponse) = binding.apply {
        setupMangaImagePreview(mangaByIDResponse)
        tvMangaName.text = mangaByIDResponse.title
        tvAlternateTitles.setOnClickListener {
            showAlternateTitlesDialog(mangaByIDResponse.alternativeTitles)
        }
        tvStartDate.text =
            mangaByIDResponse.startDate?.tryParseDate()?.formatDateDMY()
                ?: getString(R.string.n_a)
        tvEndDate.text =
            mangaByIDResponse.endDate?.tryParseDate()?.formatDateDMY()
                ?: getString(R.string.n_a)
        tvMeanScore.text = mangaByIDResponse.mean?.toString() ?: getString(R.string.n_a)
        tvRank.text = mangaByIDResponse.rank?.toString() ?: getString(R.string.n_a)
        tvPopularityRank.text = mangaByIDResponse.popularity?.toString() ?: getString(R.string.n_a)
        setupAuthorsChipGroup(mangaByIDResponse.authors)
        ibShare.setOnClickListener {
            openShareChooser(MALExternalLinks.getMangaLink(mangaByIDResponse))
        }
    }

    private fun setupAuthorsChipGroup(authors: List<MangaByIDResponse.Author>) {
        val chipGroup = binding.authorsChipGroup
        chipGroup.removeAllViews()
        if (authors.isEmpty()) {
            val textView = TextView(context)
            textView.text = getString(R.string.n_a)
            chipGroup.addView(textView)
        } else {
            authors.forEach { author ->
                val textView = TextView(context)
                textView.setTextColor(
                    ContextCompat.getColor(textView.context, R.color.colorPrimary)
                )
                textView.setTypeface(null, Typeface.BOLD)
                textView.text = getString(
                    R.string.manga_author_template,
                    author.node.firstName,
                    author.node.lastName
                )
                textView.setOnClickListener {
                    openUrl(MALExternalLinks.getMangaAuthorPageLink(author))
                }
                chipGroup.addView(textView)
            }
        }
    }

    private fun setupMangaImagePreview(mangaByIDResponse: MangaByIDResponse) = binding.apply {
        ivMangaMainPicture.load(
            mangaByIDResponse.mainPicture?.large ?: mangaByIDResponse.mainPicture?.medium,
            builder = UIConstants.AllRoundedMangaImageBuilder
        )
        ivMangaMainPicture.setOnClickListener {
            openImagesViewPager(mangaByIDResponse.pictures)
        }
    }

    private fun openImagesViewPager(pictures: List<MangaByIDResponse.Picture>) {
        val images: List<String> = pictures.map { it.large ?: it.medium }
        val action = BottomNavGraphDirections.openImageSlider(images.toTypedArray())
        navController.navigate(action)
    }

    private fun showFullSynopsisDialog(synopsis: String) =
        requireContext().showNoActionOkDialog(R.string.synopsis, synopsis)

    private fun openStatusDialog(status: String?) {
        val singleItems =
            arrayOf(getString(R.string.not_added)) + MangaStatus.malStatuses.map {
                it.getFormattedString(requireContext())
            }
        val checkedItem =
            status?.let { MangaStatus.malStatuses.indexOfFirst { it.name == status } + 1 } ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.media_set_status_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when (which) {
                    checkedItem -> Unit
                    0 -> mangaDetailsViewModel.removeFromList()
                    else -> mangaDetailsViewModel.setStatus(MangaStatus.malStatuses[which - 1])
                }
                dialog.dismiss()
            }.show()
    }

    private fun openScoreDialog(score: Int?) {
        val singleItems = (0..10).map { it.toString() }.toTypedArray()
        val checkedItem = score ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.media_set_score_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when (which) {
                    checkedItem -> Unit
                    else -> mangaDetailsViewModel.setScore(which)
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun openMangaVolumeCountDialog(totalVolumes: Int?, readVolumes: Int?) {
        if (totalVolumes == null || totalVolumes == 0) {
            showMangaVolumeDialogWithTextField(readVolumes)
        } else {
            showMangaVolumesReadListDialog(totalVolumes, readVolumes)
        }
    }

    private fun showMangaVolumesReadListDialog(totalVolumes: Int, readVolumes: Int?) {
        val singleItems = (0..totalVolumes).map { it.toString() }.toTypedArray()
        val checkedItem = readVolumes ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.manga_volume_till_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                mangaDetailsViewModel.setReadVolumeCount(which)
                dialog.dismiss()
            }.show()
    }

    @SuppressLint("InflateParams")
    private fun showMangaVolumeDialogWithTextField(readVolumes: Int?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_read_count_dialog, null)
        val binding: CustomReadCountDialogBinding =
            CustomReadCountDialogBinding.bind(view)
        binding.etNum.setText(readVolumes?.toString() ?: String.emptyString)
        MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                val count = binding.etNum.text?.toString()?.toInt() ?: 0
                mangaDetailsViewModel.setReadVolumeCount(count)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun openMangaChapterCountDialog(totalChapters: Int?, readChapters: Int?) {
        if (totalChapters == null || totalChapters == 0) {
            showMangaChapterDialogWithTextField(readChapters)
        } else {
            showMangaChapterReadListDialog(totalChapters, readChapters)
        }
    }

    private fun showMangaChapterReadListDialog(totalChapters: Int, readChapters: Int?) {
        val singleItems = (0..totalChapters).map { it.toString() }.toTypedArray()
        val checkedItem = readChapters ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.manga_chapter_till_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                mangaDetailsViewModel.setReadChapterCount(which)
                dialog.dismiss()
            }.show()
    }

    @SuppressLint("InflateParams")
    private fun showMangaChapterDialogWithTextField(readChapters: Int?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_read_count_dialog, null)
        val binding: CustomReadCountDialogBinding =
            CustomReadCountDialogBinding.bind(view)
        binding.etNum.setText(readChapters?.toString() ?: String.emptyString)
        MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                val count = binding.etNum.text?.toString()?.toInt() ?: 0
                mangaDetailsViewModel.setReadChapterCount(count)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun openBackgroundDialog(background: String?) =
        requireContext().showNoActionOkDialog(R.string.background, background)

    private fun openStatsDialog(numListUsers: Int, numScoringUsers: Int) =
        requireContext().showNoActionOkDialog(
            R.string.statistics,
            context?.getMangaStats(numListUsers, numScoringUsers)
        )

    private fun showAlternateTitlesDialog(alternativeTitles: MangaByIDResponse.AlternativeTitles?) =
        requireContext().showNoActionOkDialog(
            R.string.alternate_titles_hint,
            context?.getFormattedMangaTitlesString(alternativeTitles)
        )
}
