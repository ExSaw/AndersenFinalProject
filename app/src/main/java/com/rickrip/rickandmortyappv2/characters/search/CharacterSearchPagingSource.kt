package com.rickrip.rickandmortyappv2.characters.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickrip.rickandmortyappv2.domain.mappers.CharactersPageMapper
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.network.NetworkLayer

class CharacterSearchPagingSource(
    private val userSearch: String,
    private val localeExceptionCallback: (LocalException) -> Unit,
) : PagingSource<Int, Character>() {

    sealed class LocalException(
        val details:String
    ) : Exception() {
        object EmptySearch : LocalException(
            details = "Type smth to search!"
        )
        object NoResult: LocalException(
            details = "Nothing found!"
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        if (userSearch.isEmpty()) {
            val exception = LocalException.EmptySearch
            localeExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 1
        val prevKey = if (pageNumber == 1) null else pageNumber - 1

        //todo local search

        val request = NetworkLayer.apiClient.getCharactersPageWithName(
            name = userSearch,
            pageIndex = pageNumber
        )

        if(request.data?.code() == 404){ //noting found
            val exception = LocalException.NoResult
            localeExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        request.exception?.let {
            return LoadResult.Error(it) //400
        }

        return LoadResult.Page(
            data = request.body.results.map {
                CharactersPageMapper.buildFrom(it)
            },
            prevKey = prevKey,
            nextKey = getPageIndexFromNext(request.body.info.next)
        )

    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? { //character/?name=morty&page=1
        if (next == null) {
            return null
        }
        val remainder = next.substringAfter("page=")
        val finalIndex = if (remainder.contains('&')) {
            remainder.indexOfFirst { it == '&' }
        } else {
            remainder.length
        }
        return remainder.substring(0, finalIndex).toIntOrNull()
    }


}