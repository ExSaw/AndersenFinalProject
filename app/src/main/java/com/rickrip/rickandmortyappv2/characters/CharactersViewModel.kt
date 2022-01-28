package com.rickrip.rickandmortyappv2.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rickrip.rickandmortyappv2.util.ConstantsAndUtils
import com.rickrip.rickandmortyappv2.local.AppDatabase
import com.rickrip.rickandmortyappv2.network.response.GetCharacterByIdResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val appDatabase: AppDatabase):ViewModel() {

    private val repository = CharactersRepository(appDatabase)

    private val pageListConfig:PagedList.Config = PagedList.Config.Builder()
        .setPageSize(ConstantsAndUtils.PAGE_SIZE) //from api
        .setPrefetchDistance(ConstantsAndUtils.PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope,repository)
    val charactersPagedListLiveData:LiveData<PagedList<GetCharacterByIdResponse>> = LivePagedListBuilder(
        dataSourceFactory,pageListConfig
    ).build()
}