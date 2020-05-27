package com.cybershark.mediahub.ui.manga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.MangaModel
import com.cybershark.mediahub.ui.manga.util.MangaItemDiffUtilCallback

class AddMangaAdapter :
    RecyclerView.Adapter<AddMangaAdapter.AddMangaViewHolder>() {

    class AddMangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMangaCover = itemView.findViewById<ImageView>(R.id.ivMangaCover)!!
        private val tvMangaName = itemView.findViewById<TextView>(R.id.tvMangaName)!!
        private val tvMangaDetails = itemView.findViewById<TextView>(R.id.tvMangaDetails)!!
        private val ibAdd = itemView.findViewById<ImageButton>(R.id.ibAdd)!!

        fun bind(mangaModel: MangaModel) {
            Glide.with(ivMangaCover.context)
                .asBitmap()
                .load(mangaModel.image_url)
                .error(R.drawable.error_placeholder)
                .centerCrop()
                .transform(RoundedCorners(8))
                .into(ivMangaCover)
            tvMangaDetails.text = ("${mangaModel.total_chapters} - Ongoing")
            tvMangaName.text = mangaModel.name
            ibAdd.setOnClickListener {
                //todo:show dialog
                Toast.makeText(ibAdd.context, mangaModel.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMangaViewHolder {
        return AddMangaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.manga_search_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddMangaViewHolder, position: Int) {
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