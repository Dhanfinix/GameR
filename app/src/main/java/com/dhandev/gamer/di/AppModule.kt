package com.dhandev.gamer.di

import com.dhandev.gamer.core.domain.usecase.GamesInteractor
import com.dhandev.gamer.core.domain.usecase.GamesUseCase
import com.dhandev.gamer.detail.DetailViewModel
import com.dhandev.gamer.favorite.FavoriteViewModel
import com.dhandev.gamer.home.HomeViewModel
import com.dhandev.gamer.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GamesUseCase> { GamesInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}