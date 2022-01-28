package com.rickrip.rickandmortyappv2.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rickrip.rickandmortyappv2.domain.models.Episode
import java.lang.reflect.Type

class EpisodeListConverter {

    @TypeConverter
    fun fromEpisodeList(episodeList: List<Episode?>?): String? {
        if (episodeList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Episode?>?>() {}.getType()
        return gson.toJson(episodeList, type)
    }

    @TypeConverter
    fun toEpisodeList(episodeListString: String?): List<Episode?>? {
        if (episodeListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Episode?>?>() {}.getType()
        return gson.fromJson(episodeListString, type)
    }


}