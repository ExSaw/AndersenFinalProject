package com.rickrip.rickandmortyappv2.domain.mappers

import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.network.response.GetEpisodeByIdResponse

object EpisodeMapper {
    fun buildFrom(networkEpisode:GetEpisodeByIdResponse): Episode {
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            episode = networkEpisode.episode
        )
    }
}