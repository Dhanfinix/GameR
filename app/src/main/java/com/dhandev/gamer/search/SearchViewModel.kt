package com.dhandev.gamer.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.core.domain.usecase.GamesUseCase

class SearchViewModel (private val gamesUseCase: GamesUseCase): ViewModel() {
    fun setSearch(query: String) =
        gamesUseCase.searchGames(query)

//    val searchRes = gamesUseCase.searchGames("ONE")
}