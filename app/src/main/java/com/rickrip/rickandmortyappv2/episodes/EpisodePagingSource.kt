package com.rickrip.rickandmortyappv2.episodes

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickrip.rickandmortyappv2.domain.mappers.EpisodeMapper
import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.domain.models.EpisodePage
import com.rickrip.rickandmortyappv2.network.NetworkLayer
import com.rickrip.rickandmortyappv2.util.ConstantsAndUtils

class EpisodePagingSource(
    private val repository: EpisodeRepository
) : PagingSource<Int, Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val pageNumber = params.key ?: 1
        val prevKey = if (pageNumber == 1) null else pageNumber - 1

        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageNumber)

        pageRequest.exception?.let {

            val episodesPage = repository.getDatabase().episodeDao().getEpisodesPage(pageNumber)

            if (episodesPage == null) {

                Log.d(ConstantsAndUtils.LOG_TAG, "episodesPage==null 1")

                return LoadResult.Error(it)
            }
        }

        var episodesPage = repository.getDatabase().episodeDao().getEpisodesPage(pageNumber)
        if (episodesPage == null) {

            Log.d(ConstantsAndUtils.LOG_TAG, "episodesPage==null 2")

            pageRequest.body.page = pageNumber

            val episodePage = EpisodePage(
                // key = Math.random().toInt(),
                page = pageRequest.body.page,
                info = pageRequest.body.info,
                results = pageRequest.body.results.map {
                    EpisodeMapper.buildFrom(it)
                }
            )

            repository.getDatabase().episodeDao().insert(episodePage)
        }
        episodesPage = repository.getDatabase().episodeDao().getEpisodesPage(pageNumber)

        if (episodesPage != null) {

            Log.d(ConstantsAndUtils.LOG_TAG, "episodesPage!=null ${episodesPage.info.next}")

            return LoadResult.Page(
                data = episodesPage.results,
                prevKey = null,
                nextKey = ConstantsAndUtils.getPageIndexFromNext(episodesPage.info.next)
            )
        }

        return LoadResult.Page(
            data = pageRequest.body.results.map { EpisodeMapper.buildFrom(it) },
            prevKey = null,
            nextKey = ConstantsAndUtils.getPageIndexFromNext(pageRequest.body.info.next)

        )
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}