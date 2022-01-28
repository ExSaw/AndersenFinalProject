package com.rickrip.rickandmortyappv2.domain.mappers

import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.network.response.GetCharacterByIdResponse
import com.rickrip.rickandmortyappv2.network.response.GetEpisodeByIdResponse

object CharactersPageMapper {

    fun buildFrom(
        response: GetCharacterByIdResponse,
    ):Character{
        return Character(
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }

    fun buildTo(
        response: Character,
    ):GetCharacterByIdResponse{
        return GetCharacterByIdResponse(
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = GetCharacterByIdResponse.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = GetCharacterByIdResponse.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }

}