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
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.databinding.MangaListItemBinding

class RelatedMangaAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RelatedMangaAdapter.RelatedMangaViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<AnimeByIDResponse.RelatedManga>() {
            override fun areItemsTheSame(
                oldItem: AnimeByIDResponse.RelatedManga,
                newItem: AnimeByIDResponse.RelatedManga
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeByIDResponse.RelatedManga,
                newItem: AnimeByIDResponse.RelatedManga
            ): Boolean {
                return oldItem == newItem
            }

        }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: MangaListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedMangaViewHolder {
        binding = MangaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RelatedMangaViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RelatedMangaViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<AnimeByIDResponse.RelatedManga>) {
        listDiffer.submitList(list)
    }

    class RelatedMangaViewHolder(
        private val binding: MangaListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeByIDResponse.RelatedManga) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvMangaName.text = item.node.title
            binding.tvChapsRead.text = item.relationTypeFormatted
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