package com.rickrip.rickandmortyappv2.characters.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickrip.rickandmortyappv2.characters.CharactersRepository
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.local.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val appDatabase: AppDatabase):ViewModel() {

    private val repository = CharactersRepository(appDatabase) //access to repo

    private val _characterByIdLiveData = MutableLiveData<Character?>() //livedata
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId:Int)=viewModelScope.launch{
        val character = repository.getCharacterById(characterId) //network request
        _characterByIdLiveData.postValue(character) //after request post it ti livedata
    }

}