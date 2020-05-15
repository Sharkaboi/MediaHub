package com.cybershark.mediahub.ui.manga.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybershark.mediahub.data.models.MangaModel

class MangaViewModel : ViewModel() {

    private val _libraryDummyData = MutableLiveData<List<MangaModel>>().apply {
        value = listOf(
            MangaModel("1","Overgeared","https://mangabob.com/wp-content/uploads/2020/04/Overgeared-mangabob.jpg","Chapter 26",null,0),
            MangaModel("2","Boruto : Naruto Next Generations","https://cruncheez.files.wordpress.com/2019/10/boruto_36_2.jpg?w=784","Chapter 42",null,0),
            MangaModel("3","One Punch Man","https://images-na.ssl-images-amazon.com/images/I/81VAgJoB3BL.jpg","Chapter 102",null,0),
            MangaModel("4","Solo Levelling","https://i.pinimg.com/564x/88/ea/86/88ea86eb24f8161d58c47ed94755b4dc.jpg","Chapter 91",null,0),
            MangaModel("5","My Hero Academia","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSKTNvp8SElidSD5b4jC1z2NhsjpWh_kP_uTOcR2drNpKXRvVo1&usqp=CAU","Chapter 256",null,0),
            MangaModel("6","Kimetsu No Yaiba","https://images-na.ssl-images-amazon.com/images/I/61RLgk2k-1L._SX331_BO1,204,203,200_.jpg","Chapter 117",null,0),
            MangaModel("7","Overlord","https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1516785488l/35271520.jpg","Chapter 71",null,0),
            MangaModel("8","test","","test",null,0)
        )
    }
    val dummyData: LiveData<List<MangaModel>> = _libraryDummyData
}