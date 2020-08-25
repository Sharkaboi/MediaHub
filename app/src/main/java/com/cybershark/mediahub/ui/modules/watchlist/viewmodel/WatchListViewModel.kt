package com.cybershark.mediahub.ui.modules.watchlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybershark.mediahub.data.room.entities.WatchListModel

class WatchListViewModel : ViewModel() {

    private val _watchListDummy=MutableLiveData<List<WatchListModel>>().apply {
        value = listOf(
            WatchListModel(
                "1",
                "Food Wars S5",
                "https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",
                "S05E11 The Battle of fragrance begins!",
                25L,
                10L
            ),
            WatchListModel(
                "2",
                "Food Wars S4",
                "https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",
                null,
                25L,
                25L
            ),
            WatchListModel(
                "3",
                "Food Wars S3",
                "https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",
                null,
                25L,
                25L
            ),
            WatchListModel(
                "4",
                "Food Wars S2",
                "https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",
                null,
                25L,
                25L
            ),
            WatchListModel(
                "5",
                "Food Wars S1",
                "https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",
                null,
                25L,
                25L
            ),
            WatchListModel("6", "test", "", null, 25L, 25L)
            )
    }
    val watchListDummy: LiveData<List<WatchListModel>> = _watchListDummy
}