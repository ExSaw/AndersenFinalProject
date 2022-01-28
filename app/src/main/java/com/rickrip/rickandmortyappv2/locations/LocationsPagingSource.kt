package com.rickrip.rickandmortyappv2.locations

import android.provider.SyncStateContract
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickrip.rickandmortyappv2.domain.mappers.EpisodeMapper
import com.rickrip.rickandmortyappv2.domain.mappers.LocationMapper
import com.rickrip.rickandmortyappv2.domain.models.EpisodePage
import com.rickrip.rickandmortyappv2.domain.models.Location
import com.rickrip.rickandmortyappv2.domain.models.LocationPage
import com.rickrip.rickandmortyappv2.network.NetworkLayer
import com.rickrip.rickandmortyappv2.util.ConstantsAndUtils

class LocationsPagingSource(
    private val repository: LocationRepository
) : PagingSource<Int, Location>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val pageNumber = params.key ?: 1
        val prevKey = if (pageNumber == 1) null else pageNumber - 1


        val pageRequest = NetworkLayer.apiClient.getLocationsPage(pageNumber)

        pageRequest.exception?.let {

            val locationsPage = repository.getDatabase().locationDao().getLocationsPage(pageNumber)

            if (locationsPage == null) {
                return LoadResult.Error(it)
            }
        }

        var locationsPage = repository.getDatabase().locationDao().getLocationsPage(pageNumber)
        if (locationsPage == null) {

            pageRequest.body.page = pageNumber

            val locationPage = LocationPage(
                page = pageRequest.body.page,
                info = pageRequest.body.info,
                results = pageRequest.body.results.map{
                    LocationMapper.buildFrom(it)
                }
            )

            repository.getDatabase().locationDao().insert(locationPage)
        }
        locationsPage = repository.getDatabase().locationDao().getLocationsPage(pageNumber)

        if (locationsPage != null) {

            return LoadResult.Page(
                data = locationsPage.results,
                prevKey = null,
                nextKey = ConstantsAndUtils.getPageIndexFromNext(locationsPage.info.next)
            )
        }

        return LoadResult.Page(
            data = pageRequest.body.results.map { LocationMapper.buildFrom(it) },
            prevKey = null,
            nextKey = ConstantsAndUtils.getPageIndexFromNext(pageRequest.body.info.next)

        )

    }

    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}