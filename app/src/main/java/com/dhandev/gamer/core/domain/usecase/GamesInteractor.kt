package com.dhandev.gamer.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dhandev.gamer.core.data.Resource
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.core.domain.repository.IGamesRepository

class GamesInteractor(private val gamesRepository: IGamesRepository) : GamesUseCase {
    override fun getAllGames(): LiveData<Resource<List<Games>>> = gamesRepository.getAllGames()

    override fun searchGames(query: String): LiveData<Resource<List<Games>>> = gamesRepository.searchGames(query)

    override fun getFavGames(): LiveData<List<Games>> = gamesRepository.getFavGames()

    override fun setFavGames(games: Games, state: Boolean) = gamesRepository.setFavGames(games, state)
}