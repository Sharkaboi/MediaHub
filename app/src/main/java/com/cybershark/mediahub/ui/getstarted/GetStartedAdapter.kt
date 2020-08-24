package com.cybershark.mediahub.ui.getstarted

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cybershark.mediahub.databinding.GetStartedItemBinding

class GetStartedAdapter(private val list: List<Pair<Int, String>>) : RecyclerView.Adapter<GetStartedAdapter.PageViewHolder>() {

    private lateinit var binding: GetStartedItemBinding

    inner class PageViewHolder(private val binding: GetStartedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Pair<Int, String>) {
            binding.ivBanner.load(item.first)
            binding.tvSubtitle.text = item.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        binding = GetStartedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.setData(list[position])
    }
}