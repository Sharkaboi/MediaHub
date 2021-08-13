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
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AnimeSuggestionsAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<AnimeSuggestionsAdapter.AnimeSuggestionsViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<AnimeSuggestionsResponse.Data>() {
            override fun areItemsTheSame(
                oldItem: AnimeSuggestionsResponse.Data,
                newItem: AnimeSuggestionsResponse.Data
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeSuggestionsResponse.Data,
                newItem: AnimeSuggestionsResponse.Data
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: AnimeListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeSuggestionsViewHolder {
        binding = AnimeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeSuggestionsViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AnimeSuggestionsViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<AnimeSuggestionsResponse.Data>) {
        listDiffer.submitList(list)
    }

    class AnimeSuggestionsViewHolder(
        private val binding: AnimeListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeSuggestionsResponse.Data) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvAnimeName.text = item.node.title
            binding.tvEpisodesWatched.isVisible = false
            binding.tvScore.text = binding.tvScore.context.getRatingStringWithRating(item.node.meanScore)
            binding.ivAnimeBanner.load(
                uri = item.node.mainPicture?.large ?: item.node.mainPicture?.medium,
                builder = UIConstants.AnimeImageBuilder
            )
        }
    }
}
