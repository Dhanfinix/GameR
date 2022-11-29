package com.dhandev.gamer.core.data.source.remote.network

import com.dhandev.gamer.core.data.source.remote.response.ListGamesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getList(
        @Query("key") key: String
    ): ListGamesResponse

    @GET("games")
    suspend fun getSearch(
        @Query("key") key: String,
        @Query("search") search: String,
    ): ListGamesResponse
}
