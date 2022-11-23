package com.dhandev.gamer.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhandev.gamer.core.BuildConfig
import com.dhandev.gamer.core.data.source.remote.network.ApiResponse
import com.dhandev.gamer.core.data.source.remote.network.ApiService
import com.dhandev.gamer.core.data.source.remote.response.GamesResponse
import com.dhandev.gamer.core.data.source.remote.response.ListGamesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getAllGames() : Flow<ApiResponse<List<GamesResponse>>>{
        return flow {
            try {
                val response = apiService.getList(BuildConfig.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearch(query: String) : Flow<ApiResponse<List<GamesResponse>>>{
        return flow {
            try {
                val response = apiService.getSearch(BuildConfig.API_KEY, query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: java.lang.Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

//    fun getSearch(query: String): LiveData<ApiResponse<List<GamesResponse>>> {
//        val searchResult = MutableLiveData<ApiResponse<List<GamesResponse>>>()
//
//        //get data from remote api
//        val client = apiService.getSearch(BuildConfig.API_KEY, query)
//
//        client.enqueue(object : Callback<ListGamesResponse> {
//            override fun onResponse(
//                call: Call<ListGamesResponse>,
//                response: Response<ListGamesResponse>
//            ) {
//                val dataArray = response.body()?.results
//                searchResult.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
//            }
//
//            override fun onFailure(call: Call<ListGamesResponse>, t: Throwable) {
//                searchResult.value = ApiResponse.Error(t.message.toString())
//                Log.e("RemoteDataSource", t.message.toString())
//            }
//        })
//
//        return searchResult
//    }
}

