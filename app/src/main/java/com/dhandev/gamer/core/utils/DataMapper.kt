package com.dhandev.gamer.core.utils

import com.dhandev.gamer.core.data.source.local.entity.GamesEntity
import com.dhandev.gamer.core.data.source.remote.response.GamesResponse
import com.dhandev.gamer.core.domain.model.Games

object DataMapper {
    fun mapResponsesToEntities(input: List<GamesResponse>): List<GamesEntity> {
        val gamesList = ArrayList<GamesEntity>()
        input.map {
            val games = GamesEntity(
                id = it.id,
                rating = it.rating,
                name = it.name,
                backgroundImage = it.backgroundImage,
                released = it.released,
                isFavorite = false,
                metacritic =  it.metacritic,
                slug = it.slug,
                suggestionsCount = it.suggestionsCount
            )
            gamesList.add(games)
        }
        return gamesList
    }

    fun mapEntitiesToDomain(input: List<GamesEntity>): List<Games> =
        input.map {
            Games(
                id = it.id,
                rating = it.rating,
                name = it.name,
                backgroundImage = it.backgroundImage,
                released = it.released,
                isFavorite = it.isFavorite,
                metacritic =  it.metacritic,
                slug = it.slug,
                suggestionsCount = it.suggestionsCount
            )
        }

    fun mapDomainToEntity(input: Games) = GamesEntity(
        id = input.id,
        rating = input.rating,
        name = input.name,
        backgroundImage = input.backgroundImage,
        released = input.released,
        isFavorite = input.isFavorite,
        metacritic = input.metacritic,
        slug = input.slug,
        suggestionsCount = input.suggestionsCount
    )
}