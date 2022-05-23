package com.sharkaboi.mediahub.modules.discover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemHorizontalBinding

class AnimeRankingAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<AnimeRankingAdapter.AnimeRankingViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<AnimeRankingResponse.Data>() {
            override fun areItemsTheSame(
                oldItem: AnimeRankingResponse.Data,
                newItem: AnimeRankingResponse.Data
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeRankingResponse.Data,
                newItem: AnimeRankingResponse.Data
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: AnimeListItemHorizontalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeRankingViewHolder {
        binding = AnimeListItemHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimeRankingViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AnimeRankingViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<AnimeRankingResponse.Data>) {
        listDiffer.submitList(list)
    }

    class AnimeRankingViewHolder(
        private val binding: AnimeListItemHorizontalBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeRankingResponse.Data) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvAnimeName.text = item.node.title
            binding.tvEpisodesWatched.isVisible = false
            binding.tvScore.text =
                binding.tvScore.context.getRatingStringWithRating(item.node.meanScore)
            binding.ivAnimeBanner.load(
                item.node.mainPicture?.large ?: item.node.mainPicture?.medium,
                builder = UIConstants.TopRoundedAnimeImageBuilder
            )
        }
    }
}
