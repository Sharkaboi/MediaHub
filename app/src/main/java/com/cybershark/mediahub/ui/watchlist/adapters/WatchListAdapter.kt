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

    private var itemList = listOf<WatchListModel>()

    inner class WatchListViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val tvItemName = item.findViewById<TextView>(R.id.tvItemName)!!
        private val ivBanner = item.findViewById<ImageView>(R.id.ivBanner)!!
        private val pbEpisodes = item.findViewById<ProgressBar>(R.id.pbEpisodes)!!
        private val tvEpisodeWatchedCount =
            item.findViewById<TextView>(R.id.tvEpisodeWatchedCount)!!
        private val tvEpisodesLeft = item.findViewById<TextView>(R.id.tvEpisodesLeft)!!
        private val tvNextEpisode = item.findViewById<TextView>(R.id.tvNextEpisode)!!

        fun bind(watchListModel: WatchListModel) {
            tvItemName.text = watchListModel.name
            val nextEpisodeTitle = watchListModel.next_episode_title
            if (nextEpisodeTitle != null) {
                tvNextEpisode.visibility = View.VISIBLE
                tvNextEpisode.text = nextEpisodeTitle
            } else {
                tvNextEpisode.visibility = View.GONE
            }
            Glide.with(ivBanner.context)
                .load(watchListModel.image_url)
                .transform(RoundedCorners(8), CenterCrop())
                .error(R.drawable.error_placeholder)
                .into(ivBanner)
            val totalEpisodes = watchListModel.total_episodes
            val watchedEpisodes = watchListModel.watched_count

            val episodesLeft = totalEpisodes - watchedEpisodes
            tvEpisodesLeft.text = when (episodesLeft) {
                0L -> ("Completed !")
                else -> ("$episodesLeft Episodes left")
            }
            tvEpisodeWatchedCount.text = ("${watchedEpisodes}/${totalEpisodes}")
            pbEpisodes.progress = (watchedEpisodes.toFloat() / totalEpisodes * 100).toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        return WatchListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.watchlist_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun setItemsList(it: List<WatchListModel>) {
        itemList = it
    }

}