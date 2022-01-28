package com.rickrip.rickandmortyappv2.episodes

import com.rickrip.rickandmortyappv2.local.AppDatabase
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val database: AppDatabase) {

    fun getDatabase(): AppDatabase {
        return database
    }

}