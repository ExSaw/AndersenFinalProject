package com.rickrip.rickandmortyappv2.characters

import androidx.paging.PageKeyedDataSource
import com.rickrip.rickandmortyappv2.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val API_STARTING_PAGE_INDEX = 1

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
    ) : PageKeyedDataSource<Int,GetCharacterByIdResponse>(){

    override fun loadInitial( //when first page
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharacterPage(1)
            if(page==null){
                callback.onResult(emptyList(),null,null)
                return@launch
            }
            callback.onResult(page.results,null,getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharacterPage(params.key)

            if(page==null){
                callback.onResult(emptyList(),null)
                return@launch
            }
            callback.onResult(page.results, getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        //nothing to do
    }

    private fun getPageIndexFromNext(next:String?):Int?{
        return next?.split("?page=")?.get(1)?.toInt()
    }

}