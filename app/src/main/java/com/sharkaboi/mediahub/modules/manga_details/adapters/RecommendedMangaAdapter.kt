package com.sharkaboi.mediahub.modules.manga_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.databinding.MangaListItemBinding

class RecommendedMangaAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecommendedMangaAdapter.RecommendedMangaViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<MangaByIDResponse.Recommendation>() {
            override fun areItemsTheSame(
                oldItem: MangaByIDResponse.Recommendation,
                newItem: MangaByIDResponse.Recommendation
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: MangaByIDResponse.Recommendation,
                newItem: MangaByIDResponse.Recommendation
            ): Boolean {
                return oldItem == newItem
            }

        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: MangaListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMangaViewHolder {
        binding = MangaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedMangaViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RecommendedMangaViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<MangaByIDResponse.Recommendation>) {
        listDiffer.submitList(list)
    }

    class RecommendedMangaViewHolder(
        private val binding: MangaListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MangaByIDResponse.Recommendation) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvMangaName.text = item.node.title
            binding.tvVolumesRead.isVisible = false
            binding.tvChapsRead.text =
                ("Recommended ${item.numRecommendations} ${if (item.numRecommendations == 1) "time" else "times"}")
            binding.cardRating.isGone = true
            binding.ivMangaBanner.load(
                item.node.mainPicture?.large ?: item.node.mainPicture?.medium
            ) {
                crossfade(true)
                placeholder(R.drawable.ic_manga_placeholder)
                error(R.drawable.ic_manga_placeholder)
                transformations(RoundedCornersTransformation(topLeft = 8f, topRight = 8f))
            }
        }
    }
}