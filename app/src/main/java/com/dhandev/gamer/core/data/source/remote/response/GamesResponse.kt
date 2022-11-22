package com.dhandev.gamer.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("metacritic")
    val metacritic: Double,

    @field:SerializedName("suggestions_count")
    val suggestionsCount: Int,

    @field:SerializedName("released")
    val released: String

)
