package com.cybershark.mediahub.ui.watchlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybershark.mediahub.data.models.WatchListModel

class WatchListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is watchlist Fragment"
    }
    private val _watchListDummy=MutableLiveData<List<WatchListModel>>().apply {
        value = listOf(
            WatchListModel("Food Wars S5","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg","S05E11 The Battle of fragrance begins!",25L,10L),
            WatchListModel("Food Wars S4","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S3","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S2","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L),
            WatchListModel("Food Wars S1","https://upload.wikimedia.org/wikipedia/en/d/d3/Shokugeki_no_Souma_Volume_1.jpg",null,25L,25L)
            )
    }
    val text: LiveData<String> = _text
    val watchListDummy: LiveData<List<WatchListModel>> = _watchListDummy
}