package com.dhandev.gamer.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class FavoriteViewModel (gamesUseCase: GamesUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favorite Fragment"
    }
    val text: LiveData<String> = _text
}