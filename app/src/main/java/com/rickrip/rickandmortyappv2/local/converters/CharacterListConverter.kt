package com.rickrip.rickandmortyappv2.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.domain.models.Episode
import java.lang.reflect.Type

class CharacterListConverter {

    @TypeConverter
    fun fromCharacterList(charList: List<Character?>?): String? {
        if (charList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Character?>?>() {}.getType()
        return gson.toJson(charList, type)
    }

    @TypeConverter
    fun toCharacterList(charListString: String?): List<Character?>? {
        if (charListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Character?>?>() {}.getType()
        return gson.fromJson(charListString, type)
    }


}