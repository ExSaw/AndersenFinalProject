package com.rickrip.rickandmortyappv2.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.domain.models.Location
import java.lang.reflect.Type

class LocationListConverter {

    @TypeConverter
    fun fromLocationList(locationList: List<Location?>?): String? {
        if (locationList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Location?>?>() {}.getType()
        return gson.toJson(locationList, type)
    }

    @TypeConverter
    fun toLocationList(locationListString: String?): List<Location?>? {
        if (locationListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Location?>?>() {}.getType()
        return gson.fromJson(locationListString, type)
    }


}