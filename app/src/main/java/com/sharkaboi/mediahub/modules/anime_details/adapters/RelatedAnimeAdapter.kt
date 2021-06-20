package com.sharkaboi.mediahub.modules.anime_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class RelatedAnimeAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RelatedAnimeAdapter.RelatedAnimeViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<AnimeByIDResponse.RelatedAnime>() {
            override fun areItemsTheSame(
                oldItem: AnimeByIDResponse.RelatedAnime,
                newItem: AnimeByIDResponse.RelatedAnime
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeByIDResponse.RelatedAnime,
                newItem: AnimeByIDResponse.RelatedAnime
            ): Boolean {
                return oldItem == newItem
            }

        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: AnimeListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedAnimeViewHolder {
        binding = AnimeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RelatedAnimeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RelatedAnimeViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<AnimeByIDResponse.RelatedAnime>) {
        listDiffer.submitList(list)
    }

    class RelatedAnimeViewHolder(
        private val binding: AnimeListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeByIDResponse.RelatedAnime) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvAnimeName.text = item.node.title
            binding.tvEpisodesWatched.text = item.relationTypeFormatted
            binding.cardRating.isGone = true
            binding.ivAnimeBanner.load(
                item.node.mainPicture?.large ?: item.node.mainPicture?.medium
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_anime_placeholder)
                error(R.drawable.ic_anime_placeholder)
                transformations(RoundedCornersTransformation(topLeft = 8f, topRight = 8f))
            }
        }
    }
}