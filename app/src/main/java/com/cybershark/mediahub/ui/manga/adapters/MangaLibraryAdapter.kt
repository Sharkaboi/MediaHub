package com.cybershark.mediahub.ui.manga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.MangaModel


class MangaLibraryAdapter : RecyclerView.Adapter<MangaLibraryAdapter.MangaLibraryViewHolder>() {
    private var itemsList = listOf<MangaModel>()

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
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MangaLibraryViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun setAdapterList(itemsList: List<MangaModel>) {
        this.itemsList = itemsList
    }
}