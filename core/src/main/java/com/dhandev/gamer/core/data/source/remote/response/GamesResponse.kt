package com.dhandev.gamer.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("background_image")
    val backgroundImage: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("metacritic")
    val metacritic: Double? = null,

    @field:SerializedName("suggestions_count")
    val suggestionsCount: Int? = null,

    @field:SerializedName("released")
    val released: String? = null

)
