package com.sharkaboi.mediahub.common.constants

import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import com.sharkaboi.mediahub.R

object UIConstants {
    const val AnimeAndMangaGridSpanCount = 3
    private const val AnimeAndMangaImageCornerRadius = 8f
    private const val ProfileImageCornerRadius = 10f
    private val ChipShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
    val AnimeImageBuilder: ImageRequest.Builder.() -> Unit = {
        crossfade(true)
        placeholder(R.drawable.ic_anime_placeholder)
        error(R.drawable.ic_anime_placeholder)
        fallback(R.drawable.ic_anime_placeholder)
        transformations(
            RoundedCornersTransformation(
                radius = AnimeAndMangaImageCornerRadius
            )
        )
    }
    val MangaImageBuilder: ImageRequest.Builder.() -> Unit = {
        crossfade(true)
        placeholder(R.drawable.ic_manga_placeholder)
        error(R.drawable.ic_manga_placeholder)
        fallback(R.drawable.ic_manga_placeholder)
        transformations(
            RoundedCornersTransformation(
                radius = AnimeAndMangaImageCornerRadius
            )
        )
    }
    val ProfileImageBuilder: ImageRequest.Builder.() -> Unit = {
        crossfade(true)
        placeholder(R.drawable.ic_profile_placeholder)
        error(R.drawable.ic_profile_placeholder)
        fallback(R.drawable.ic_profile_placeholder)
        transformations(RoundedCornersTransformation(ProfileImageCornerRadius))
    }

    fun Chip.setMediaHubChipStyle(): Chip {
        return this.apply {
            setEnsureMinTouchTargetSize(false)
            shapeAppearanceModel = ChipShapeAppearanceModel
        }
    }
}
