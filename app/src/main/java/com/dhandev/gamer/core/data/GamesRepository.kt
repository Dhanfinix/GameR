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
                    Log.e("Games It is null???", it.toString())
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Games>?): Boolean = data == null ||data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<GamesResponse>>> =
                remoteDataSource.getAllGames()

            override fun saveCallResult(data: List<GamesResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gameList)
            }

        }.asLiveData()

    override fun searchGames(query: String): LiveData<Resource<List<Games>>> =
        object: NetworkBoundResource<List<Games>, List<GamesResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<List<Games>> {
                return Transformations.map(localDataSource.getSearch(query)){
                    Log.e("Search result null???", it.toString())
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Games>?): Boolean {
                val should = data == null || data.isEmpty()
                Log.e("SHouLD FETCH", should.toString())
                return should
            }

            override fun createCall(): LiveData<ApiResponse<List<GamesResponse>>> =
                remoteDataSource.getSearch(query)

            override fun saveCallResult(data: List<GamesResponse>) {
                val gameList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gameList)
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