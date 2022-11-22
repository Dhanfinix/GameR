package com.dhandev.gamer.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhandev.gamer.core.data.source.local.entity.GamesEntity

@Dao
interface GamesDao {

    @Query("SELECT * FROM GAMES")
    fun getAllGames() : LiveData<List<GamesEntity>>

    @Query("SELECT * FROM GAMES WHERE ISFAVORITE = 1")
    fun getFavGames() : LiveData<List<GamesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<GamesEntity>)

    @Update
    fun updateFavGames(games: GamesEntity)

}