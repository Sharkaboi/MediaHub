package com.cybershark.mediahub.ui.modules.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.room.entities.MangaModel
import com.cybershark.mediahub.databinding.MangaUpdatesItemBinding
import com.cybershark.mediahub.ui.modules.manga.util.MangaItemDiffUtilCallback

class MangaUpdatesAdapter : RecyclerView.Adapter<MangaUpdatesAdapter.MangaUpdatesViewHolder>() {

    private lateinit var binding: MangaUpdatesItemBinding

    class MangaUpdatesViewHolder(private val binding: MangaUpdatesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mangaModel: MangaModel) {
            binding.ivMangaCover.load(mangaModel.image_url) {
                error(R.drawable.error_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            binding.tvChapterName.text = mangaModel.latest_chap_title
            binding.tvMangaName.text = mangaModel.name
            binding.ibInfo.setOnClickListener {
                //todo:show dialog
                Toast.makeText(binding.ibInfo.context, mangaModel.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaUpdatesViewHolder {
        binding = MangaUpdatesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaUpdatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MangaUpdatesViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    fun setAdapterList(itemsList: List<MangaModel>) {
        listDiffer.submitList(itemsList)
    }

    private val listDiffer = AsyncListDiffer(this, MangaItemDiffUtilCallback)

}