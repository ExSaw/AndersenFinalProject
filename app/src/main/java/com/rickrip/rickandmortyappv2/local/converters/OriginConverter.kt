package com.rickrip.rickandmortyappv2.local.converters

import androidx.room.TypeConverter
import com.rickrip.rickandmortyappv2.domain.models.Character

class OriginConverter {
    @TypeConverter
    fun fromString(location: String): Character.Origin {
        val list = location.split(",")
        return Character.Origin(list[0], list[1])
    }

    @TypeConverter
    fun toString(origin: Character.Origin): String {
        val name = origin.name
        val url = origin.url
        return "$name,$url"
    }
}