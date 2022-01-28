package com.rickrip.rickandmortyappv2.network.response

data class GetCharacterByIdResponse(
    val created: String = "",
    val episode: List<String> = listOf(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val location: Location,
    val name: String = "",
    val origin: Origin,
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
){
    data class Location(
        val name: String = "",
        val url: String = ""
    )
    data class Origin(
        val name: String = "",
        val url: String = ""
    )
}