package com.rickrip.rickandmortyappv2.locations

import com.rickrip.rickandmortyappv2.local.AppDatabase
import javax.inject.Inject

class LocationRepository @Inject constructor(private val database: AppDatabase) {

    fun getDatabase(): AppDatabase {
        return database
    }


}