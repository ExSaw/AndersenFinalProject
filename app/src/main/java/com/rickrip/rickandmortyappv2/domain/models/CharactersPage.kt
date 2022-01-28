package com.rickrip.rickandmortyappv2.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickrip.rickandmortyappv2.network.response.PageInfo

@Entity(tableName = "characters_page")
data class CharactersPage(
    //@PrimaryKey(autoGenerate = true) val key: Int,
    @PrimaryKey val page: Int,
    val info: PageInfo,
    val results: List<Character>,
)