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

    inner class MangaLibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMangaLibCover = itemView.findViewById<ImageView>(R.id.ivMangaLibCover)!!
        val tvMangaLibName = itemView.findViewById<TextView>(R.id.tvMangaLibName)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaLibraryViewHolder {
        return MangaLibraryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.manga_library_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MangaLibraryViewHolder, position: Int) {
        holder.apply {
            val imageUrl=itemsList[position].image_url
            Glide.with(ivMangaLibCover.context).asBitmap().load(imageUrl).error(R.drawable.error_placeholder).into(ivMangaLibCover)
            tvMangaLibName.text=itemsList[position].name
        }
    }

    fun setAdapterList(itemsList: List<MangaModel>) {
        this.itemsList = itemsList
    }
}