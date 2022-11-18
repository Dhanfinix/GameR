package com.dhandev.gamer.core.data.source.local

import androidx.lifecycle.LiveData
import com.dhandev.gamer.core.data.source.local.entity.GamesEntity
import com.dhandev.gamer.core.data.source.local.room.GamesDao

class LocalDataSource private constructor(private val gamesDao: GamesDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(gamesDao: GamesDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(gamesDao)
            }
    }

    fun getAllGames(): LiveData<List<GamesEntity>> = gamesDao.getAllGames()

    fun getFavoriteGames(): LiveData<List<GamesEntity>> = gamesDao.getFavGames()

    fun insertGames(gamesList: List<GamesEntity>) = gamesDao.insertGames(gamesList)

    fun setFavoriteGames(games: GamesEntity, newState: Boolean) {
        games.isFavorite = newState
        gamesDao.updateFavGames(games)
    }
}