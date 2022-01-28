package com.rickrip.rickandmortyappv2.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickrip.rickandmortyappv2.domain.models.Location
import com.rickrip.rickandmortyappv2.domain.models.LocationPage

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAllLocations(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationPage: LocationPage)

    @Query("SElECT * FROM location WHERE id=:id")
    fun getLocationById(id: Int): Location?

    @Query("SELECT * FROM location WHERE name LIKE :name")
    fun getLocationByName(name: String): Location?

    @Query("SELECT * FROM locations_page WHERE page LIKE :page")
    fun getLocationsPage(page: Int): LocationPage?


}