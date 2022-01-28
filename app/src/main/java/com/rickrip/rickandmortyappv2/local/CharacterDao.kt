package com.rickrip.rickandmortyappv2.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.domain.models.CharactersPage
import com.rickrip.rickandmortyappv2.domain.models.EpisodePage

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CHARACTERS")
    fun getAllCharacter() : List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPage(charactersPage: CharactersPage)

    @Query("SElECT * FROM CHARACTERS WHERE id=:id")
    fun getCharacterById(id : Int) : Character?

    @Query("SELECT * FROM CHARACTERS WHERE name LIKE :name")
    fun getCharacterByName(name : String) : Character?

    @Query("SELECT * FROM characters_page WHERE page LIKE :page")
    fun getCharactersPage(page : Int) : CharactersPage?
}