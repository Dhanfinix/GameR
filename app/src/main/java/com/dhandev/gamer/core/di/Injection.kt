package com.dhandev.gamer.core.di

import android.content.Context
import com.dhandev.gamer.core.data.GamesRepository
import com.dhandev.gamer.core.data.source.local.LocalDataSource
import com.dhandev.gamer.core.data.source.local.room.GamesDatabase
import com.dhandev.gamer.core.data.source.remote.RemoteDataSource
import com.dhandev.gamer.core.data.source.remote.network.ApiConfig
import com.dhandev.gamer.core.domain.repository.IGamesRepository
import com.dhandev.gamer.core.domain.usecase.GamesInteractor
import com.dhandev.gamer.core.domain.usecase.GamesUseCase
import com.dhandev.gamer.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IGamesRepository {
        val database = GamesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.gamesDao())
        val appExecutors = AppExecutors()

        return GamesRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideTourismUseCase(context: Context): GamesUseCase {
        val repository = provideRepository(context)
        return GamesInteractor(repository)
    }
}