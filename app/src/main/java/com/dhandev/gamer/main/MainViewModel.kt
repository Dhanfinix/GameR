package com.dhandev.gamer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class MainViewModel (gamesUseCase: GamesUseCase): ViewModel() {
    val games = gamesUseCase.getAllGames().asLiveData()
}