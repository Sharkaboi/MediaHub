package com.sharkaboi.mediahub.modules.anime_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class RecommendedAnimeAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecommendedAnimeAdapter.RecommendedAnimeViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<AnimeByIDResponse.Recommendation>() {
            override fun areItemsTheSame(
                oldItem: AnimeByIDResponse.Recommendation,
                newItem: AnimeByIDResponse.Recommendation
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeByIDResponse.Recommendation,
                newItem: AnimeByIDResponse.Recommendation
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: AnimeListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedAnimeViewHolder {
        binding = AnimeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedAnimeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RecommendedAnimeViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<AnimeByIDResponse.Recommendation>) {
        listDiffer.submitList(list)
    }

    class RecommendedAnimeViewHolder(
        private val binding: AnimeListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeByIDResponse.Recommendation) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvAnimeName.text = item.node.title
            binding.tvEpisodesWatched.text =
                binding.tvEpisodesWatched.context.resources.getQuantityString(
                    R.plurals.recommendation_times,
                    item.numRecommendations,
                    item.numRecommendations
                )
            binding.cardRating.isGone = true
            binding.ivAnimeBanner.load(
                uri = item.node.mainPicture?.large ?: item.node.mainPicture?.medium,
                builder = UIConstants.AnimeImageBuilder
            )
        }
    }
}
