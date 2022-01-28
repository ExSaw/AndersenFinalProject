package com.rickrip.rickandmortyappv2.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.domain.models.EpisodePage

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM EPISODES")
    fun getAllEpisodes() : List<Episode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(episodePage: EpisodePage)

    @Query("SElECT * FROM EPISODES WHERE id=:id")
    fun getEpisodeById(id : Int) : Episode?

    @Query("SELECT * FROM EPISODES WHERE name LIKE :name")
    fun getEpisodeByName(name : String) : Episode?

    @Query("SELECT * FROM episodes_page WHERE page LIKE :page")
    fun getEpisodesPage(page: Int): EpisodePage?


}