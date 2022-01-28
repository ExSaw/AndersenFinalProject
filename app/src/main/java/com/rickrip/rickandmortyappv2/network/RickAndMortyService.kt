package com.rickrip.rickandmortyappv2.network

import com.rickrip.rickandmortyappv2.network.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/{char-id}")
    suspend fun getCharacterById(
        @Path("char-id") charId: Int
    ): Response<GetCharacterByIdResponse>

    @GET("character/")
    suspend fun getCharactersPage(
        @Query("page") pageIndex:Int
    ): Response<GetCharactersPageResponse>

    @GET("character/")
    suspend fun getCharactersPage(
        @Query("name") name:String,
        @Query("page") pageIndex:Int
    ): Response<GetCharactersPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId:Int
    ): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeRange:String
    ): Response<List<GetEpisodeByIdResponse>>

    @GET("episode/")
    suspend fun getEpisodesPage(
        @Query("page") pageIndex:Int
    ): Response<GetEpisodesPageResponse>

    @GET("location/")
    suspend fun getLocationsPage(
        @Query("page") pageIndex:Int
    ): Response<GetLocationsPageResponse>

}