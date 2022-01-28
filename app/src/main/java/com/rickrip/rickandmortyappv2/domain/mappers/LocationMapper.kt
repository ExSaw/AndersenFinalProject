package com.rickrip.rickandmortyappv2.domain.mappers


import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.domain.models.Location
import com.rickrip.rickandmortyappv2.network.response.GetLocationByIdResponse

object LocationMapper {
    fun buildFrom(networkLocation:GetLocationByIdResponse): Location {
        return Location(
            name = networkLocation.name,
            dimension = networkLocation.dimension,
            characters = networkLocation.characters,
            created = networkLocation.created,
            type = networkLocation.type,
            id = networkLocation.id
        )
    }
}