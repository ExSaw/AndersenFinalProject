package com.rickrip.rickandmortyappv2.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rickrip.rickandmortyappv2.util.ConstantsAndUtils
import com.rickrip.rickandmortyappv2.local.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(private val database: AppDatabase):ViewModel() {

    private val repository = EpisodeRepository(database)
    val flow = Pager(
        PagingConfig(
            pageSize = ConstantsAndUtils.PAGE_SIZE,
            prefetchDistance = ConstantsAndUtils.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ){
        EpisodePagingSource(repository)
    }.flow.cachedIn(viewModelScope)

}