package com.cybershark.mediahub.ui.modules.watchlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.room.entities.WatchListModel
import com.cybershark.mediahub.databinding.WatchlistItemBinding
import com.cybershark.mediahub.ui.modules.watchlist.util.WatchListDiffUtilItemCallback

class WatchListAdapter : RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder>() {

    private lateinit var binding: WatchlistItemBinding

    inner class WatchListViewHolder(private val binding: WatchlistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(watchListModel: WatchListModel) {
            binding.tvItemName.text = watchListModel.name
            val nextEpisodeTitle = watchListModel.next_episode_title
            if (nextEpisodeTitle != null) {
                binding.tvNextEpisode.isVisible = true
                binding.tvNextEpisode.text = nextEpisodeTitle
            } else {
                binding.tvNextEpisode.isGone = true
            }
            binding.ivBanner.load(watchListModel.image_url){
                error(R.drawable.error_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            val totalEpisodes = watchListModel.total_episodes
            val watchedEpisodes = watchListModel.watched_count

            val episodesLeft = totalEpisodes - watchedEpisodes
            binding.tvEpisodesLeft.text = when (episodesLeft) {
                0L -> ("Completed !")
                else -> ("$episodesLeft Episodes left")
            }
            binding.tvEpisodeWatchedCount.text = ("${watchedEpisodes}/${totalEpisodes}")
            binding.pbEpisodes.progress = (watchedEpisodes.toFloat() / totalEpisodes * 100).toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        binding = WatchlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    fun setItemsList(list: List<WatchListModel>) {
        listDiffer.submitList(list)
    }

    private val listDiffer = AsyncListDiffer(this, WatchListDiffUtilItemCallback)
}