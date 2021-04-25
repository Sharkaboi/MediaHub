package com.sharkaboi.mediahub.modules.anime_details.ui

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.databinding.FragmentAnimeDetailsBinding
import com.sharkaboi.mediahub.modules.anime_details.vm.AnimeDetailsState
import com.sharkaboi.mediahub.modules.anime_details.vm.AnimeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AnimeDetailsFragment : Fragment() {
    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!
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
                is AnimeDetailsState.ProfileFailure -> showToast(uiState.message)
                else -> Unit
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
                animeByIDResponse.startDate?.tryParseDate()?.formatDate() ?: getString(R.string.n_a)
            tvEndDate.text =
                animeByIDResponse.endDate?.tryParseDate()?.formatDate() ?: getString(R.string.n_a)
            tvMeanScore.text = animeByIDResponse.mean?.toString() ?: getString(R.string.n_a)
            tvRank.text = animeByIDResponse.rank?.toString() ?: getString(R.string.n_a)
            tvPopularityRank.text = animeByIDResponse.popularity.toString()
            ivAnimeMainPicture.load(
                animeByIDResponse.mainPicture?.large ?: animeByIDResponse.mainPicture?.medium
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_anime_placeholder)
                error(R.drawable.ic_anime_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            otherDetails.tvSynopsis.text = animeByIDResponse.synopsis ?: getString(R.string.n_a)
            otherDetails.tvNsfwRating.text =
                animeByIDResponse.nsfw?.getNsfwRating() ?: getString(R.string.n_a)
            otherDetails.tvPgRating.text =
                animeByIDResponse.rating?.getRating() ?: getString(R.string.n_a)
            animeByIDResponse.genres.let {
                if (it.isEmpty()) {
                    otherDetails.genresChipGroup.addView(Chip(context).apply {
                        text = getString(R.string.n_a)
                    })
                } else {
                    it.forEach { genre ->
                        otherDetails.genresChipGroup.addView(Chip(context).apply {
                            text = genre.name
                        })
                    }
                }
            }
            otherDetails.tvMediaType.text = animeByIDResponse.mediaType.capitalize(Locale.ROOT)
            otherDetails.tvAnimeCurrentStatus.text = animeByIDResponse.status.getStatus()
            otherDetails.tvTotalEps.text =
                animeByIDResponse.numEpisodes.let {
                    if (it == 0)
                        getString(R.string.n_a)
                    else
                        it.toString()
                }
            otherDetails.tvSeason.text = animeByIDResponse.startSeason?.let {
                "${it.season} ${it.year}"
            } ?: getString(R.string.n_a)
            otherDetails.tvSchedule.text = animeByIDResponse.broadcast?.getBroadcastTime() ?: getString(R.string.n_a)
        }
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

private fun AnimeByIDResponse.AlternativeTitles.getFormattedString(): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(
            """
            <b>English title</b> : ${
                this.en?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Japanese title</b> : ${
                this.ja?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Synonyms</b> : ${
                this.synonyms?.let {
                    if (it.isEmpty()) {
                        "N/A"
                    } else {
                        it.joinToString()
                    }
                } ?: "N/A"
            }
        """.trimIndent(), Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        Html.fromHtml(
            """
            <b>English title</b> : ${
                this.en?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Japanese title</b> : ${
                this.ja?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Synonyms</b> : ${
                this.synonyms?.let {
                    if (it.isEmpty()) {
                        "N/A"
                    } else {
                        it.joinToString()
                    }
                } ?: "N/A"
            }
        """.trimIndent()
        )
    }
}
