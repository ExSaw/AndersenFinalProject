package com.rickrip.rickandmortyappv2.di

import android.app.Application
import androidx.room.Room
import com.rickrip.rickandmortyappv2.characters.CharactersRepository
import com.rickrip.rickandmortyappv2.local.AppDatabase
import com.rickrip.rickandmortyappv2.local.CharacterDao
import com.rickrip.rickandmortyappv2.local.EpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)

@Module
 object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, "character_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun providesCharacterDao(appDatabase: AppDatabase): CharacterDao{
        return appDatabase.characterDao()
    }

    @Provides
    fun providesCharactersRepository(appDatabase: AppDatabase): CharactersRepository{
        return CharactersRepository(appDatabase)
    }

}