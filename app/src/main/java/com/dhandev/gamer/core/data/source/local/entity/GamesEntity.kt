package com.dhandev.gamer.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GamesEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "backgroundImage")
    var backgroundImage: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)
