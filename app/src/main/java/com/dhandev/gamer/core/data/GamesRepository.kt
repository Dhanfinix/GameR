package com.dhandev.gamer.core.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dhandev.gamer.core.data.source.local.LocalDataSource
import com.dhandev.gamer.core.data.source.remote.RemoteDataSource
import com.dhandev.gamer.core.data.source.remote.network.ApiResponse
import com.dhandev.gamer.core.data.source.remote.response.GamesResponse
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.core.domain.repository.IGamesRepository
import com.dhandev.gamer.core.utils.AppExecutors
import com.dhandev.gamer.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GamesRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGamesRepository {

    override fun getAllGames(): Flow<Resource<List<Games>>> =
        object: NetworkBoundResource<List<Games>, List<GamesResponse>>(appExecutors){
            override fun loadFromDB(): Flow<List<Games>> {
                return localDataSource.getAllGames().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Games>?): Boolean = data == null ||data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GamesResponse>>> =
                remoteDataSource.getAllGames()

            override suspend fun saveCallResult(data: List<GamesResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gameList)
            }

        }.asFlow()

    override fun searchGames(query: String): Flow<Resource<List<Games>>> =
        object: NetworkBoundResource<List<Games>, List<GamesResponse>>(appExecutors){
            override fun loadFromDB(): Flow<List<Games>> {
                return localDataSource.getSearch(query).map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Games>?): Boolean {
//                val should = data == null || data.isEmpty()
//                Log.e("SHouLD FETCH", should.toString())
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GamesResponse>>> =
                remoteDataSource.getSearch(query)

            override suspend fun saveCallResult(data: List<GamesResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gameList)
            }

        }.asFlow()


    override fun getFavGames(): Flow<List<Games>> {
        return localDataSource.getFavoriteGames().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavGames(games: Games, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(games)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGames(tourismEntity, state) }
    }
}