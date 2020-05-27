package com.cybershark.mediahub.ui.manga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.MangaModel
import com.cybershark.mediahub.ui.manga.util.MangaItemDiffUtilCallback


class MangaLibraryAdapter : RecyclerView.Adapter<MangaLibraryAdapter.MangaLibraryViewHolder>() {

    class MangaLibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMangaLibCover = itemView.findViewById<ImageView>(R.id.ivMangaLibCover)!!
        private val tvMangaLibName = itemView.findViewById<TextView>(R.id.tvMangaLibName)!!

        fun bind(mangaModel: MangaModel) {
            Glide.with(ivMangaLibCover.context)
                .asBitmap()
                .load(mangaModel.image_url)
                .error(R.drawable.error_placeholder)
                .into(ivMangaLibCover)
            tvMangaLibName.text = mangaModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaLibraryViewHolder {
        return MangaLibraryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.manga_library_item, parent, false)
        )
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