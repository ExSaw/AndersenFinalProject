package com.rickrip.rickandmortyappv2.util

object ConstantsAndUtils {

    val LOG_TAG = "LOG_TAG"
    val BASE_URL = "https://rickandmortyapi.com/api/"
    val PAGE_SIZE = 20
    val PREFETCH_DISTANCE = PAGE_SIZE * 2

    fun getPageIndexFromNext(next: String?): Int? {
        return try {
            next?.split("?page=")?.get(1)?.toInt()
        } catch (e: Exception) {
            null
        }
    }
}