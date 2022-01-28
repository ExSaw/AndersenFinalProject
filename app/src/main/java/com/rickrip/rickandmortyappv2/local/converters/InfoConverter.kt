package com.rickrip.rickandmortyappv2.local.converters

import android.graphics.pdf.PdfDocument
import androidx.room.TypeConverter
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.domain.models.EpisodePage
import com.rickrip.rickandmortyappv2.network.response.PageInfo

class InfoConverter {
    @TypeConverter
    fun fromInfo(episodeInfo: String): PageInfo {
        val list = episodeInfo.split(",")
        return PageInfo(list[0].toInt(), list[1].toInt(), list[2],list[3])
    }

    @TypeConverter
    fun toInfo(episodeInfo: PageInfo): String {
        val count = episodeInfo.count.toString()
        val pages = episodeInfo.pages.toString()
        val next = episodeInfo.next
        val prev = episodeInfo.prev
        return "$count,$pages,$next,$prev"
    }
}