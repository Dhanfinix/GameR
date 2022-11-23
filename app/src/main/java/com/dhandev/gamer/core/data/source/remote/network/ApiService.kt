package com.dhandev.gamer.core.data.source.remote.network

import com.dhandev.gamer.core.data.source.remote.response.ListGamesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    fun getList(
        @Query("key") key: String
    ): Call<ListGamesResponse>

    @GET("games")
    fun getSearch(
        @Query("key") key: String,
        @Query("search") search: String,
    ): Call<ListGamesResponse>
}
