package com.cybershark.mediahub.ui.modules.manga.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cybershark.mediahub.data.models.MangaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaViewModel : ViewModel() {

    private val _libraryDummyData = MutableLiveData<List<MangaModel>>().apply {
        viewModelScope.launch { value = getDummyList() }
    }
    val dummyData: LiveData<List<MangaModel>> = _libraryDummyData

    private val _addMangaSearchList = MutableLiveData<List<MangaModel>>().apply {
        //todo load from api on query
        value = listOf()
    }
    val addMangaSearchList = _addMangaSearchList

    fun getDummySearchList(keyword: String) {
        _addMangaSearchList.value = dummyData.value?.filter { it.name.toLowerCase().contains(keyword.toLowerCase()) }
    }

    suspend fun updateLibraryFromRep() {
    //TODO("Not yet implemented")
    }

    suspend fun updateNewChaptersFromRep() {
    //TODO("Not yet implemented")
    }

    fun testUpdate() {
    val list = mutableListOf<MangaModel>()
        list.add(MangaModel("0", "test", "", "test", null, 0))
        list.add(MangaModel("1", "Overgeared", "https://mangabob.com/wp-content/uploads/2020/04/Overgeared-mangabob.jpg", "Chapter 26", null, 0))
        list.add(MangaModel("2", "Boruto : Naruto Next Generations", "https://cruncheez.files.wordpress.com/2019/10/boruto_36_2.jpg?w=784", "Chapter 42", null, 0))
        list.add(MangaModel("3", "One Punch Man", "https://images-na.ssl-images-amazon.com/images/I/81VAgJoB3BL.jpg", "Chapter 102", null, 0))
        list.add(MangaModel("4", "Solo Levelling", "https://i.pinimg.com/564x/88/ea/86/88ea86eb24f8161d58c47ed94755b4dc.jpg", "Chapter 91", null, 0))
        list.add(MangaModel("5", "My Hero Academia", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSKTNvp8SElidSD5b4jC1z2NhsjpWh_kP_uTOcR2drNpKXRvVo1&usqp=CAU", "Chapter 256", null, 0))
        list.add(MangaModel("6", "Kimetsu No Yaiba", "https://images-na.ssl-images-amazon.com/images/I/61RLgk2k-1L._SX331_BO1,204,203,200_.jpg", "Chapter 117", null, 0))
        list.add(MangaModel("7", "Overlord", "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1516785488l/35271520.jpg", "Chapter 71", null, 0))
        _libraryDummyData.value = list
    }

    private suspend fun getDummyList(): MutableList<MangaModel> = withContext(Dispatchers.Default) {
        val list = mutableListOf<MangaModel>()
        list.add(MangaModel("1", "Overgeared", "https://mangabob.com/wp-content/uploads/2020/04/Overgeared-mangabob.jpg", "Chapter 26", null, 0))
        list.add(MangaModel("2", "Boruto : Naruto Next Generations", "https://cruncheez.files.wordpress.com/2019/10/boruto_36_2.jpg?w=784", "Chapter 42", null, 0))
        list.add(MangaModel("3", "One Punch Man", "https://images-na.ssl-images-amazon.com/images/I/81VAgJoB3BL.jpg", "Chapter 102", null, 0))
        list.add(MangaModel("4", "Solo Levelling", "https://i.pinimg.com/564x/88/ea/86/88ea86eb24f8161d58c47ed94755b4dc.jpg", "Chapter 91", null, 0))
        list.add(MangaModel("5", "My Hero Academia", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSKTNvp8SElidSD5b4jC1z2NhsjpWh_kP_uTOcR2drNpKXRvVo1&usqp=CAU", "Chapter 256", null, 0))
        list.add(MangaModel("6", "Kimetsu No Yaiba", "https://images-na.ssl-images-amazon.com/images/I/61RLgk2k-1L._SX331_BO1,204,203,200_.jpg", "Chapter 117", null, 0))
        list.add(MangaModel("7", "Overlord", "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1516785488l/35271520.jpg", "Chapter 71", null, 0))
        list.add(MangaModel("8", "test", "", "test", null, 0))
        list.add(MangaModel("9", "test", "", "test", null, 0))
        list.add(MangaModel("10", "test", "", "test", null, 0))
        list.add(MangaModel("11", "test", "", "test", null, 0))
        list.add(MangaModel("12", "test", "", "test", null, 0))
        list.add(MangaModel("13", "test", "", "test", null, 0))
        return@withContext list
    }
}