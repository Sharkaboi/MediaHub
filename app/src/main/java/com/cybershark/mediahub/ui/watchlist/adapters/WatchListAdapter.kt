package com.cybershark.mediahub.ui.watchlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.WatchListModel

class WatchListAdapter : RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder>() {

    lateinit var itemList: List<WatchListModel>

    inner class WatchListViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvItemName=item.findViewById<TextView>(R.id.tvItemName)!!
        val ivBanner=item.findViewById<ImageView>(R.id.ivBanner)!!
        val pbEpisodes=item.findViewById<ProgressBar>(R.id.pbEpisodes)!!
        val tvEpisodeWatchedCount=item.findViewById<TextView>(R.id.tvEpisodeWatchedCount)!!
        val tvEpisodesLeft=item.findViewById<TextView>(R.id.tvEpisodesLeft)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        return WatchListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.watchlist_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.apply {
            tvItemName.text=itemList[position].name
            Glide.with(ivBanner.context).load(itemList[position].image_url).transform(RoundedCorners(8),CenterCrop()).error(R.drawable.error_placeholder).into(ivBanner)
            tvEpisodesLeft.text=("${itemList[position].total_episodes-itemList[position].watched_count} Episodes left")
            tvEpisodeWatchedCount.text=("${itemList[position].watched_count}/${itemList[position].total_episodes}")
            pbEpisodes.progress=(itemList[position].watched_count.toFloat()/itemList[position].total_episodes*100).toInt()
        }
    }

}