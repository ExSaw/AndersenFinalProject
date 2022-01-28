package com.rickrip.rickandmortyappv2.network.response

data class GetLocationByIdResponse(
    val dimension: String = "",
    val characters: List<String> = listOf(),
    val created: String = "",
    val type: String = "",
    val id: Int = 0,
    val name: String = "",
    val url: String = ""
)