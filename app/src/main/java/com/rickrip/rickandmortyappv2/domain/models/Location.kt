package com.rickrip.rickandmortyappv2.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    val dimension: String = "",
    val characters: List<String> = listOf(),
    val created: String = "",
    val type: String = "",
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val url: String = ""
)