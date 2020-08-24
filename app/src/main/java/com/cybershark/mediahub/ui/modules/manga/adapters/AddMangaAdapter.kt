package com.cybershark.mediahub.ui.modules.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.entities.MangaModel
import com.cybershark.mediahub.databinding.MangaSearchItemBinding
import com.cybershark.mediahub.ui.modules.manga.util.MangaItemDiffUtilCallback

class AddMangaAdapter :
    RecyclerView.Adapter<AddMangaAdapter.AddMangaViewHolder>() {

    private lateinit var binding: MangaSearchItemBinding

    class AddMangaViewHolder(private val binding: MangaSearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mangaModel: MangaModel) {
            binding.ivMangaCover.load(mangaModel.image_url){
                error(R.drawable.error_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            binding.tvMangaDetails.text = ("${mangaModel.total_chapters} - Ongoing")
            binding.tvMangaName.text = mangaModel.name
            binding.ibAdd.setOnClickListener {
                //todo:show dialog
                Toast.makeText(binding.ibAdd.context, mangaModel.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMangaViewHolder {
        binding = MangaSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddMangaViewHolder(binding)
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