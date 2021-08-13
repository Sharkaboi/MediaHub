package com.sharkaboi.mediahub.modules.manga_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.databinding.MangaListItemBinding

class RelatedMangaAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RelatedMangaAdapter.RelatedMangaViewHolder>() {

    private val diffUtilItemCallback =
        object : DiffUtil.ItemCallback<MangaByIDResponse.RelatedManga>() {
            override fun areItemsTheSame(
                oldItem: MangaByIDResponse.RelatedManga,
                newItem: MangaByIDResponse.RelatedManga
            ): Boolean {
                return oldItem.node.id == newItem.node.id
            }

            override fun areContentsTheSame(
                oldItem: MangaByIDResponse.RelatedManga,
                newItem: MangaByIDResponse.RelatedManga
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

    fun submitList(list: List<MangaByIDResponse.RelatedManga>) {
        listDiffer.submitList(list)
    }

    class RelatedMangaViewHolder(
        private val binding: MangaListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MangaByIDResponse.RelatedManga) {
            binding.root.setOnClickListener {
                onClick(item.node.id)
            }
            binding.tvMangaName.text = item.node.title
            binding.tvChapsRead.text = item.relationTypeFormatted
            binding.cardRating.isGone = true
            binding.tvVolumesRead.isVisible = false
            binding.ivMangaBanner.load(
                uri = item.node.mainPicture?.large ?: item.node.mainPicture?.medium,
                builder = UIConstants.MangaImageBuilder
            )
        }
    }
}
