package com.dhandev.gamer.detail

import androidx.lifecycle.ViewModel
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class DetailViewModel (private val gamesUseCase: GamesUseCase): ViewModel() {
    fun setFavorite(games: Games, newStatus: Boolean)=
        gamesUseCase.setFavGames(games, newStatus)
}