package com.dhandev.gamer.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhandev.gamer.BuildConfig
import com.dhandev.gamer.core.data.source.remote.network.ApiResponse
import com.dhandev.gamer.core.data.source.remote.network.ApiService
import com.dhandev.gamer.core.data.source.remote.response.GamesResponse
import com.dhandev.gamer.core.data.source.remote.response.ListGamesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllTourism(): LiveData<ApiResponse<List<GamesResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<GamesResponse>>>()

        //get data from remote api
        val client = apiService.getList(BuildConfig.API_KEY)

        client.enqueue(object : Callback<ListGamesResponse> {
            override fun onResponse(
                call: Call<ListGamesResponse>,
                response: Response<ListGamesResponse>
            ) {
                val dataArray = response.body()?.results
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListGamesResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}

