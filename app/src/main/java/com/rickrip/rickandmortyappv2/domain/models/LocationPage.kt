package com.rickrip.rickandmortyappv2.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickrip.rickandmortyappv2.network.response.PageInfo

@Entity(tableName = "locations_page")
data class LocationPage(
    @PrimaryKey val page: Int,
    val info: PageInfo,
    val results: List<Location>,
)