package com.rickrip.rickandmortyappv2.network.response


data class GetCharactersPageResponse(
    var page:Int = 0,
    val info: PageInfo = PageInfo(),
    val results: List<GetCharacterByIdResponse> = emptyList()
)