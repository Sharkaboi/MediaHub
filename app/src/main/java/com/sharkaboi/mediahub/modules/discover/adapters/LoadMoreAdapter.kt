package com.sharkaboi.mediahub.modules.discover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharkaboi.mediahub.databinding.LoadMoreItemBinding

class LoadMoreAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<LoadMoreAdapter.LoadMoreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadMoreViewHolder {
        val binding =
            LoadMoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadMoreViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    class LoadMoreViewHolder(
        private val binding: LoadMoreItemBinding,
        private val onClick: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.tvMore.setOnClickListener {
                onClick()
            }
        }
    }
}