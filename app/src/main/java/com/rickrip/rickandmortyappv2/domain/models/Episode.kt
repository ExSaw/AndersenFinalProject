package com.rickrip.rickandmortyappv2.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val episode: String = ""
)