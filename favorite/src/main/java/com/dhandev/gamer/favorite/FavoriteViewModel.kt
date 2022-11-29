package com.dhandev.gamer.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class FavoriteViewModel(gamesUseCase: GamesUseCase) : ViewModel() {
    val favoriteGames = gamesUseCase.getFavGames().asLiveData()
}