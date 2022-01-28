package com.rickrip.rickandmortyappv2.network

import com.rickrip.rickandmortyappv2.network.response.*
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(characterId: Int): NetworkResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(pageIndex: Int): NetworkResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPage(pageIndex) }
    }

    suspend fun getCharactersPageWithName(
        name:String,
        pageIndex: Int
    ): NetworkResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPage(name,pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): NetworkResponse<GetEpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): NetworkResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodesPage(pageIndex: Int):NetworkResponse<GetEpisodesPageResponse>{
        return safeApiCall { rickAndMortyService.getEpisodesPage(pageIndex) }
    }

    suspend fun getLocationsPage(pageIndex: Int):NetworkResponse<GetLocationsPageResponse>{
        return safeApiCall { rickAndMortyService.getLocationsPage(pageIndex) }
    }


    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): NetworkResponse<T> {
        return try {
            NetworkResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            NetworkResponse.failure(e)
        }
    }

}
