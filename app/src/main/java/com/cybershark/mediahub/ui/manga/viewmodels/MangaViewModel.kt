package com.cybershark.mediahub.ui.manga.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MangaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is manga Fragment"
    }
    val text: LiveData<String> = _text
}