package com.dhandev.gamer.core.data

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

class GamesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGamesRepository {

    companion object {
        @Volatile
        private var instance: GamesRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): GamesRepository =
            instance ?: synchronized(this) {
                instance ?: GamesRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllGames(): LiveData<Resource<List<Games>>> =
        object: NetworkBoundResource<List<Games>, List<GamesResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<List<Games>> {
                return Transformations.map(localDataSource.getAllGames()){
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Games>?): Boolean = data == null ||data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<GamesResponse>>> =
                remoteDataSource.getAllTourism()

            override fun saveCallResult(data: List<GamesResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(tourismList)
            }

        }.asLiveData()

    override fun getFavGames(): LiveData<List<Games>> {
        return Transformations.map(localDataSource.getFavoriteGames()){
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavGames(games: Games, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(games)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGames(tourismEntity, state) }
    }
}