package com.example.wattoit.main.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }

    val text: LiveData<String> = _text
}
