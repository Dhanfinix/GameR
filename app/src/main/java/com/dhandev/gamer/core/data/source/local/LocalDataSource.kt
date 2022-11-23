package com.dhandev.gamer.core.data.source.local

import androidx.lifecycle.LiveData
import com.dhandev.gamer.core.data.source.local.entity.GamesEntity
import com.dhandev.gamer.core.data.source.local.room.GamesDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gamesDao: GamesDao) {

    fun getAllGames(): Flow<List<GamesEntity>> = gamesDao.getAllGames()

    fun getSearch(query: String): Flow<List<GamesEntity>> = gamesDao.getSearch(query)

    fun getFavoriteGames(): Flow<List<GamesEntity>> = gamesDao.getFavGames()

    suspend fun insertGames(gamesList: List<GamesEntity>) = gamesDao.insertGames(gamesList)

    fun setFavoriteGames(games: GamesEntity, newState: Boolean) {
        games.isFavorite = newState
        gamesDao.updateFavGames(games)
    }
}