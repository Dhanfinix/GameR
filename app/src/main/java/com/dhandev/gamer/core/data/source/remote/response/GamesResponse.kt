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

    @field:SerializedName("released")
    val released: String

)
