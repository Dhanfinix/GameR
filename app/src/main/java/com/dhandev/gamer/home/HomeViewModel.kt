package com.dhandev.gamer.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class HomeViewModel (gamesUseCase: GamesUseCase): ViewModel() {
    val games = gamesUseCase.getAllGames()
}