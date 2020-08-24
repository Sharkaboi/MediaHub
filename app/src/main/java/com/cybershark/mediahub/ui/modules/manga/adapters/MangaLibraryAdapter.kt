package com.cybershark.mediahub.ui.modules.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.entities.MangaModel
import com.cybershark.mediahub.databinding.MangaLibraryItemBinding
import com.cybershark.mediahub.ui.modules.manga.util.MangaItemDiffUtilCallback

class MangaLibraryAdapter : RecyclerView.Adapter<MangaLibraryAdapter.MangaLibraryViewHolder>() {

    private lateinit var binding: MangaLibraryItemBinding

    class MangaLibraryViewHolder(private val binding: MangaLibraryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mangaModel: MangaModel) {
            binding.ivMangaLibCover.load(mangaModel.image_url){
                error(R.drawable.error_placeholder)
            }
            binding.tvMangaLibName.text = mangaModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaLibraryViewHolder {
        binding = MangaLibraryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MangaLibraryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: MangaLibraryViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    fun setAdapterList(itemsList: List<MangaModel>) {
        listDiffer.submitList(itemsList)
    }

    private val listDiffer = AsyncListDiffer(this, MangaItemDiffUtilCallback)
}