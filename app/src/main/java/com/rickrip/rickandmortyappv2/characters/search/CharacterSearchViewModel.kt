package com.rickrip.rickandmortyappv2.characters.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rickrip.rickandmortyappv2.util.ConstantsAndUtils
import com.rickrip.rickandmortyappv2.util.Event

class CharacterSearchViewModel : ViewModel() {

    private var curSearchText: String = ""
    private var pagingSrc: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true) {
                field = CharacterSearchPagingSource(curSearchText) {
                    Log.d(ConstantsAndUtils.LOG_TAG, it.toString())
                    _localExceptionEventLiveData.postValue(Event(it))
                }
            }

            return field
        }

    val flow = Pager(
        PagingConfig(
            pageSize = ConstantsAndUtils.PAGE_SIZE,
            prefetchDistance = ConstantsAndUtils.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSrc!!
    }.flow.cachedIn(viewModelScope)

    // error handling
    private val _localExceptionEventLiveData =
        MutableLiveData<Event<CharacterSearchPagingSource.LocalException>>()
    val localExceptionEventLiveData:
            LiveData<Event<CharacterSearchPagingSource.LocalException>> =
        _localExceptionEventLiveData

    fun submitSearchQuery(searchText: String) {
        curSearchText = searchText
        pagingSrc?.invalidate()

    }

}