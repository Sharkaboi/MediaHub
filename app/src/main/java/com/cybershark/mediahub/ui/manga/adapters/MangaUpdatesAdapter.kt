package com.cybershark.mediahub.ui.manga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.MangaModel

class MangaUpdatesAdapter : RecyclerView.Adapter<MangaUpdatesAdapter.MangaUpdatesViewHolder>() {

    private var itemsList = listOf<MangaModel>()

    class MangaUpdatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMangaCover = itemView.findViewById<ImageView>(R.id.ivMangaCover)!!
        private val tvMangaName = itemView.findViewById<TextView>(R.id.tvMangaName)!!
        private val tvChapterName = itemView.findViewById<TextView>(R.id.tvChapterName)!!
        private val ibInfo = itemView.findViewById<ImageButton>(R.id.ibInfo)!!

        fun bind(mangaModel: MangaModel) {
            Glide.with(ivMangaCover.context)
                .asBitmap()
                .load(mangaModel.image_url)
                .error(R.drawable.error_placeholder)
                .centerCrop()
                .transform(RoundedCorners(8))
                .into(ivMangaCover)
            tvChapterName.text = mangaModel.latest_chap_title
            tvMangaName.text = mangaModel.name
            ibInfo.setOnClickListener {
                //todo:show dialog
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaUpdatesViewHolder {
        return MangaUpdatesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.manga_updates_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MangaUpdatesViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun setAdapterList(itemsList: List<MangaModel>) {
        this.itemsList = itemsList
    }
}