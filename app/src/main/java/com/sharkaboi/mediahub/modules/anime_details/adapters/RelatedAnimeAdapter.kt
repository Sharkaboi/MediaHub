package com.sharkaboi.mediahub.modules.anime_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemHorizontalBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedAnimeViewHolder {
        val binding = AnimeListItemHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
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
        private val binding: AnimeListItemHorizontalBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardRating.isGone = true
        }

        fun bind(item: AnimeByIDResponse.RelatedAnime) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvAnimeName.text = item.node.title
            binding.tvEpisodesWatched.text = item.relationTypeFormatted
            binding.ivAnimeBanner.load(
                item.node.mainPicture?.large ?: item.node.mainPicture?.medium,
                builder = UIConstants.TopRoundedAnimeImageBuilder
            )
        }
    }
}
