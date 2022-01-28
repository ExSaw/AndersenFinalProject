package com.rickrip.rickandmortyappv2.characters

import com.rickrip.rickandmortyappv2.domain.mappers.CharacterMapper
import com.rickrip.rickandmortyappv2.domain.mappers.CharactersPageMapper
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.domain.models.CharactersPage
import com.rickrip.rickandmortyappv2.local.AppDatabase
import com.rickrip.rickandmortyappv2.network.NetworkLayer
import com.rickrip.rickandmortyappv2.network.response.GetCharacterByIdResponse
import com.rickrip.rickandmortyappv2.network.response.GetCharactersPageResponse
import com.rickrip.rickandmortyappv2.network.response.GetEpisodeByIdResponse
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val database: AppDatabase) {

    suspend fun getCharacterPage(pageIndex: Int): GetCharactersPageResponse? {

        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)
        var charactersPage = database.characterDao().getCharactersPage(pageIndex)

        request.exception?.let {

            if (charactersPage == null) {
                return null
            }
        }

        if (charactersPage == null) {
            charactersPage = CharactersPage(
                //  key = Math.random().toInt(),
                page = pageIndex,
                info = request.body.info,
                results = request.body.results.map {
                    CharactersPageMapper.buildFrom(it)
                }
            )

            database.characterDao().insertPage(charactersPage)
        }

        val charactersPageResult = database.characterDao().getCharactersPage(pageIndex)

        val result = charactersPageResult?.let {
            GetCharactersPageResponse(
                page = it.page,
                info = it.info,
                results = it.results.map { CharactersPageMapper.buildTo(it) },
            )
        }

        return result
    }

    suspend fun getCharacterById(characterId: Int): Character? {

        // check db for char
        val savedCharacter = database.characterDao().getCharacterById(characterId)

        if (savedCharacter != null) {
            return savedCharacter
        }


        val request = NetworkLayer.apiClient.getCharacterById(characterId) //net

        return when {

            !request.isSuccessful || request.failed -> null

            else -> {
                val networkEpisodes = getEpisodesFromCharacterResponse(request.body)
                val character = CharacterMapper.buildFrom(
                    response = request.body,
                    episodes = networkEpisodes
                )

                //update db
                database.characterDao().insert(character)

                return character

            }
        }
    }

    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString() //: [1,2,3]

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        return when {

            !request.isSuccessful || request.failed -> emptyList()

            else -> request.body
        }
    }
}