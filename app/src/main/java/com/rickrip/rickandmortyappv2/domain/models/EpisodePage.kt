package com.rickrip.rickandmortyappv2.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickrip.rickandmortyappv2.network.response.PageInfo

@Entity(tableName = "episodes_page")
data class EpisodePage(
    //@PrimaryKey val key: Int,
    @PrimaryKey val page: Int,
    val info: PageInfo,
    val results: List<Episode>,
)