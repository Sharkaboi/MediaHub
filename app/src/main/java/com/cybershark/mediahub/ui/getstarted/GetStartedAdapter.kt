package com.cybershark.mediahub.ui.getstarted

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cybershark.mediahub.R
import kotlinx.android.synthetic.main.get_started_item.view.*

class GetStartedAdapter(private val list: List<Pair<Int, String>>) : RecyclerView.Adapter<GetStartedAdapter.PageViewHolder>() {

    inner class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return PageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.get_started_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(ivBanner).load(list[position].first).into(ivBanner)
            tvSubtitle.text = list[position].second
        }
    }
}