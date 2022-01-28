package com.rickrip.rickandmortyappv2.util

/**
* https://habr.com/ru/post/468749/
 */

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read / write isn't allowed

    // Returns the content and prevents its use again.
    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Returns the content, even if it's already been handled.
    fun peekContent(): T = content
}