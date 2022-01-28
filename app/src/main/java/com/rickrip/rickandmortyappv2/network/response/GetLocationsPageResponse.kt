package com.rickrip.rickandmortyappv2.network.response

data class GetLocationsPageResponse(
    var page:Int = 0,
    val info: PageInfo = PageInfo(),
    val results: List<GetLocationByIdResponse> = emptyList()
)