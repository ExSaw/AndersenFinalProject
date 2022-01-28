package com.rickrip.rickandmortyappv2.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    val created: String = "",
    val episodeList: List<Episode> = listOf(),
    val gender: String = "",
    @PrimaryKey val id: Int = 0,
    val image: String = "",
    val location: Location,
    val name: String = "",
    val origin: Origin,
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
) {
    data class Location(
        val name: String = "",
        val url: String = ""
    )

    data class Origin(
        val name: String = "",
        val url: String = ""
    )
}
