package com.dhandev.gamer.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class SearchViewModel (gamesUseCase: GamesUseCase): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }
    val text: LiveData<String> = _text
}