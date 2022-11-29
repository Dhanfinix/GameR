package com.dhandev.gamer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Games(
    val id: Int,
    val rating: Double?,
    val name: String?,
    val backgroundImage: String?,
    val released: String?,
    val slug: String?,
    val metacritic: Double?,
    val suggestionsCount: Int?,
    val isFavorite: Boolean?
) : Parcelable
