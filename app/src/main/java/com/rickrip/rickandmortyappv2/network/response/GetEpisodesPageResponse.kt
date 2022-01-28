package com.rickrip.rickandmortyappv2.network.response

data class GetEpisodesPageResponse(
    var page:Int = 0,
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = emptyList()
)