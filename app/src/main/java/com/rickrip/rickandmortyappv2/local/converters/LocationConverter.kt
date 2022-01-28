package com.rickrip.rickandmortyappv2.local.converters

import androidx.room.TypeConverter
import com.rickrip.rickandmortyappv2.domain.models.Character

class LocationConverter {
    @TypeConverter
    fun fromString(location: String): Character.Location {
        val list = location.split(",")
        return Character.Location(list[0], list[1])
    }

    @TypeConverter
    fun toString(location: Character.Location): String {
        val name = location.name
        val url = location.url
        return "$name,$url"
    }
}