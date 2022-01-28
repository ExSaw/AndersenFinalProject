package com.rickrip.rickandmortyappv2.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rickrip.rickandmortyappv2.domain.models.*
import com.rickrip.rickandmortyappv2.local.converters.*

@Database(
    entities = [
        Character::class,
        Episode::class,
        EpisodePage::class,
        CharactersPage::class,
        Location::class,
        LocationPage::class
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        StringListConverter::class,
        OriginConverter::class,
        LocationConverter::class,
        LocationListConverter::class,
        EpisodeListConverter::class,
        InfoConverter::class,
        CharacterListConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationDao(): LocationDao

}