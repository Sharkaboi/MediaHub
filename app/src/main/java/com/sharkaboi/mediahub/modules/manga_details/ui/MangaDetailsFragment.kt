package com.sharkaboi.mediahub.modules.manga_details.ui

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
import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.databinding.CustomReadCountDialogBinding
import com.sharkaboi.mediahub.databinding.FragmentMangaDetailsBinding
import com.sharkaboi.mediahub.modules.manga_details.adapters.RecommendedMangaAdapter
import com.sharkaboi.mediahub.modules.manga_details.adapters.RelatedAnimeAdapter
import com.sharkaboi.mediahub.modules.manga_details.adapters.RelatedMangaAdapter
import com.sharkaboi.mediahub.modules.manga_details.vm.MangaDetailsState
import com.sharkaboi.mediahub.modules.manga_details.vm.MangaDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MangaDetailsFragment : Fragment() {
    private var _binding: FragmentMangaDetailsBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val args: MangaDetailsFragmentArgs by navArgs()
    private val mangaDetailsViewModel by viewModels<MangaDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        setUpObservers()
    }

    private fun setUpObservers() {
        mangaDetailsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.progressBar.isShowing = uiState is MangaDetailsState.Loading
            when (uiState) {
                is MangaDetailsState.Idle -> mangaDetailsViewModel.getMangaDetails(args.mangaId)
                is MangaDetailsState.FetchSuccess -> setData(uiState.mangaByIDResponse)
                is MangaDetailsState.MangaDetailsFailure -> showToast(uiState.message)
                else -> Unit
            }
        }
        mangaDetailsViewModel.mangaDetailsUpdate.observe(viewLifecycleOwner) { mangaDetails ->
            binding.mangaDetailsUserListCard.apply {
                btnStatus.text =
                    mangaDetails.mangaStatus?.getFormattedString()
                        ?: getString(R.string.not_added)
                btnScore.text = ("${mangaDetails.score ?: 0}/10")
                btnCountVolumes.text = ("${mangaDetails.numReadVolumes ?: 0}/${
                    if (mangaDetails.totalVolumes == 0)
                        "??"
                    else
                        mangaDetails.totalVolumes.toString()
                }")
                btnCountChaps.text = ("${mangaDetails.numReadChapters ?: 0}/${
                    if (mangaDetails.totalChapters == 0)
                        "??"
                    else
                        mangaDetails.totalChapters.toString()
                }")
                btnScore.setOnClickListener {
                    openScoreDialog(mangaDetails.score)
                }
                btnStatus.setOnClickListener {
                    openStatusDialog(mangaDetails.mangaStatus?.name, mangaDetails.mangaId)
                }
                btnCountVolumes.setOnClickListener {
                    openMangaVolumeCountDialog(
                        mangaDetails.totalVolumes,
                        mangaDetails.numReadVolumes
                    )
                }
                btnCountChaps.setOnClickListener {
                    openMangaChapterCountDialog(
                        mangaDetails.totalChapters,
                        mangaDetails.numReadChapters
                    )
                }
            }
        }
    }

    private fun setData(mangaByIDResponse: MangaByIDResponse) {
        binding.apply {
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
            tvPopularityRank.text = mangaByIDResponse.popularity.toString()
            tvAuthors.text =
                mangaByIDResponse.authors.joinToString { "${it.node.firstName} ${it.node.lastName}" }
            ivMangaMainPicture.load(
                mangaByIDResponse.mainPicture?.large ?: mangaByIDResponse.mainPicture?.medium
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_manga_placeholder)
                error(R.drawable.ic_manga_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            ivMangaMainPicture.setOnClickListener {
                openImagesViewPager(mangaByIDResponse.pictures)
            }
            otherDetails.tvSynopsis.text = mangaByIDResponse.synopsis ?: getString(R.string.n_a)
            otherDetails.tvSynopsis.setOnClickListener {
                showFullSynopsisDialog(mangaByIDResponse.synopsis ?: getString(R.string.n_a))
            }
            otherDetails.tvNsfwRating.text =
                mangaByIDResponse.nsfw?.getNsfwRating() ?: getString(R.string.n_a)
            mangaByIDResponse.genres.let {
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
            otherDetails.tvMediaType.text = mangaByIDResponse.mediaType.uppercase(Locale.ROOT)
            otherDetails.tvMangaCurrentStatus.text =
                mangaByIDResponse.status.getMangaPublishStatus()
            otherDetails.tvTotalVolumes.text =
                mangaByIDResponse.numVolumes.let {
                    if (it == 0)
                        getString(R.string.n_a)
                    else
                        it.toString()
                }
            otherDetails.tvTotalChapters.text =
                mangaByIDResponse.numChapters.let {
                    if (it == 0)
                        getString(R.string.n_a)
                    else
                        it.toString()
                }
            otherDetails.tvSerialization.text =
                mangaByIDResponse.serialization.joinToString { it.node.name }
            otherDetails.tvBackground.setOnClickListener {
                openBackgroundDialog(mangaByIDResponse.background)
            }
            otherDetails.tvStatistics.setOnClickListener {
                openStatsDialog(mangaByIDResponse.numListUsers, mangaByIDResponse.numScoringUsers)
            }
            otherDetails.rvRecommendations.apply {
                adapter = RecommendedMangaAdapter { mangaId ->
                    val action = MangaDetailsFragmentDirections.openMangaDetailsWithId(mangaId)
                    navController.navigate(action)
                }.apply {
                    submitList(mangaByIDResponse.recommendations)
                }
                otherDetails.tvRecommendations.isVisible =
                    mangaByIDResponse.recommendations.isNotEmpty()
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
            }
            otherDetails.rvRelatedAnime.apply {
                adapter = RelatedAnimeAdapter { animeId ->
                    val action = MangaDetailsFragmentDirections.openAnimeDetailsWithId(animeId)
                    navController.navigate(action)
                }.apply {
                    submitList(mangaByIDResponse.relatedAnime)
                }
                otherDetails.tvRelatedAnimeHint.isVisible =
                    mangaByIDResponse.relatedAnime.isNotEmpty()
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
            }
            otherDetails.rvRelatedManga.apply {
                adapter = RelatedMangaAdapter { mangaId ->
                    val action = MangaDetailsFragmentDirections.openMangaDetailsWithId(mangaId)
                    navController.navigate(action)
                }.apply {
                    submitList(mangaByIDResponse.relatedManga)
                }
                otherDetails.tvRelatedMangaHint.isVisible =
                    mangaByIDResponse.relatedManga.isNotEmpty()
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                itemAnimator = DefaultItemAnimator()
            }
            mangaDetailsUserListCard.apply {
                btnScore.setOnClickListener {
                    openScoreDialog(mangaByIDResponse.myListStatus?.score)
                }
                btnStatus.setOnClickListener {
                    openStatusDialog(mangaByIDResponse.myListStatus?.status, mangaByIDResponse.id)
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
                    mangaDetailsViewModel.submitStatusUpdate(mangaByIDResponse.id)
                }
            }
        }
    }

    private fun openImagesViewPager(pictures: List<MangaByIDResponse.Picture>) {
        val images: List<String> = pictures.map { it.large ?: it.medium }
        val action = MangaDetailsFragmentDirections.openImages(images.toTypedArray())
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

    private fun openStatusDialog(status: String?, mangaId: Int) {
        val singleItems =
            arrayOf("Not added") + MangaStatus.malStatuses.map { it.getFormattedString() }
        val checkedItem =
            status?.let { MangaStatus.malStatuses.indexOfFirst { it.name == status } + 1 } ?: 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Set status as")
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when (which) {
                    checkedItem -> Unit
                    0 -> mangaDetailsViewModel.removeFromList(mangaId)
                    else -> mangaDetailsViewModel.setStatus(MangaStatus.malStatuses[which - 1])
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
                    else -> mangaDetailsViewModel.setScore(which)
                }
                dialog.dismiss()
            }
            .show()
    }

    @SuppressLint("InflateParams")
    private fun openMangaVolumeCountDialog(totalVolumes: Int?, readVolumes: Int?) {
        if (totalVolumes == null || totalVolumes == 0) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.custom_read_count_dialog, null)
            val binding: CustomReadCountDialogBinding =
                CustomReadCountDialogBinding.bind(view)
            binding.etNum.setText(readVolumes?.toString() ?: "")
            MaterialAlertDialogBuilder(requireContext())
                .setView(binding.root)
                .setPositiveButton("Ok") { dialog, _ ->
                    val count = binding.etNum.text?.toString()?.toInt() ?: 0
                    mangaDetailsViewModel.setReadVolumeCount(count)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            val singleItems = (0..totalVolumes).map { it.toString() }.toTypedArray()
            val checkedItem = readVolumes ?: 0
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Read till volume")
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    mangaDetailsViewModel.setReadVolumeCount(which)
                    dialog.dismiss()
                }
                .show()
        }
    }

    @SuppressLint("InflateParams")
    private fun openMangaChapterCountDialog(totalChapters: Int?, readChapters: Int?) {
        if (totalChapters == null || totalChapters == 0) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.custom_read_count_dialog, null)
            val binding: CustomReadCountDialogBinding =
                CustomReadCountDialogBinding.bind(view)
            binding.etNum.setText(readChapters?.toString() ?: "")
            MaterialAlertDialogBuilder(requireContext())
                .setView(binding.root)
                .setPositiveButton("Ok") { dialog, _ ->
                    val count = binding.etNum.text?.toString()?.toInt() ?: 0
                    mangaDetailsViewModel.setReadChapterCount(count)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            val singleItems = (0..totalChapters).map { it.toString() }.toTypedArray()
            val checkedItem = readChapters ?: 0
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Read till chapter")
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    mangaDetailsViewModel.setReadChapterCount(which)
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

    private fun openStatsDialog(numListUsers: Int, numScoringUsers: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Statistics")
            .setMessage(
                """
                    Added in $numListUsers manga lists
                    Scored by $numScoringUsers users
                """.trimIndent()
            ).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showAlternateTitlesDialog(alternativeTitles: MangaByIDResponse.AlternativeTitles?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alternate titles")
            .setMessage(
                alternativeTitles?.getFormattedString() ?: getString(R.string.n_a)
            ).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}