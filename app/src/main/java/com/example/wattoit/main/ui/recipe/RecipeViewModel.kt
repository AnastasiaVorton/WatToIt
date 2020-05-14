package com.example.wattoit.main.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }

    val text: LiveData<String> = _text
}
