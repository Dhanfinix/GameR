package com.dhandev.gamer.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dhandev.gamer.core.data.Resource
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.core.domain.repository.IGamesRepository
import kotlinx.coroutines.flow.Flow

class GamesInteractor(private val gamesRepository: IGamesRepository) : GamesUseCase {
    override fun getAllGames(): Flow<Resource<List<Games>>> = gamesRepository.getAllGames()

    override fun searchGames(query: String): Flow<Resource<List<Games>>> = gamesRepository.searchGames(query)

    override fun getFavGames(): Flow<List<Games>> = gamesRepository.getFavGames()

    override fun setFavGames(games: Games, state: Boolean) = gamesRepository.setFavGames(games, state)
}