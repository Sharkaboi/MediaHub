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
import com.sharkaboi.mediahub.databinding.MangaListItemHorizontalBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedMangaViewHolder {
        val binding = MangaListItemHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
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
        private val binding: MangaListItemHorizontalBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardRating.isGone = true
        }

        fun bind(item: AnimeByIDResponse.RelatedManga) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvMangaName.text = item.node.title
            binding.tvChapsRead.text = item.relationTypeFormatted
            binding.ivMangaBanner.load(
                item.node.mainPicture?.large ?: item.node.mainPicture?.medium,
                builder = UIConstants.TopRoundedMangaImageBuilder
            )
        }
    }
}
