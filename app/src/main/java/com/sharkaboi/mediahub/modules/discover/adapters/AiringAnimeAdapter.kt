package com.sharkaboi.mediahub.modules.discover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.common.extensions.roundOfString
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AiringAnimeAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<AiringAnimeAdapter.AiringAnimeViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<AnimeSeasonalResponse.Data>() {
            override fun areItemsTheSame(
                oldItem: AnimeSeasonalResponse.Data,
                newItem: AnimeSeasonalResponse.Data
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeSeasonalResponse.Data,
                newItem: AnimeSeasonalResponse.Data
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: AnimeListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringAnimeViewHolder {
        binding = AnimeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AiringAnimeViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AiringAnimeViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<AnimeSeasonalResponse.Data>) {
        listDiffer.submitList(list)
    }

    class AiringAnimeViewHolder(
        private val binding: AnimeListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeSeasonalResponse.Data) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvAnimeName.text = item.node.title
            binding.tvEpisodesWatched.isVisible = false
            binding.tvScore.text = ("★ ${item.node.meanScore?.roundOfString() ?: "0"}")
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