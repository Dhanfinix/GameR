package com.dhandev.gamer.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dhandev.gamer.core.data.Resource
import com.dhandev.gamer.core.domain.model.Games

interface GamesUseCase {
    fun getAllGames() :LiveData<Resource<List<Games>>>
    fun getFavGames() :LiveData<List<Games>>
    fun setFavGames(games: Games, state: Boolean)
}