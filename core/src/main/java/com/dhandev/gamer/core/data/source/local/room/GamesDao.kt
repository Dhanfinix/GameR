package com.dhandev.gamer.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhandev.gamer.core.data.source.local.entity.GamesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Query("SELECT * FROM GAMES")
    fun getAllGames() : Flow<List<GamesEntity>>

    @Query("SELECT * FROM GAMES WHERE ISFAVORITE = 1")
    fun getFavGames() : Flow<List<GamesEntity>>

    @Query("SELECT * FROM GAMES WHERE NAME LIKE '%' || :name || '%'")
    fun getSearch(name: String?) : Flow<List<GamesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GamesEntity>)

    @Update
    fun updateFavGames(games: GamesEntity)

}