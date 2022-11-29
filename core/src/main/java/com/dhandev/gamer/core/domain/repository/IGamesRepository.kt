package com.dhandev.gamer.core.domain.repository

import com.dhandev.gamer.core.domain.model.Games
import kotlinx.coroutines.flow.Flow

interface IGamesRepository {
    fun getAllGames() : Flow<com.dhandev.gamer.core.data.Resource<List<Games>>>
    fun searchGames(query: String) : Flow<com.dhandev.gamer.core.data.Resource<List<Games>>>
    fun getFavGames() :Flow<List<Games>>
    fun setFavGames(games: Games, state: Boolean)
}