package com.dhandev.gamer.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class SearchViewModel (private val gamesUseCase: GamesUseCase): ViewModel() {
    fun setSearch(query: String) =
        gamesUseCase.searchGames(query).asLiveData()

}