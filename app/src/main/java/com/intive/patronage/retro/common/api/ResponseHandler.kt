package com.intive.patronage.retro.common.api

import retrofit2.HttpException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> = Resource.success(data)

    // fun <T : Any> handleException(): Resource<T> = Resource.error("Something went wrong", null)
    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            400 -> "You are not the board owner"
            else -> "Something went wrong"
        }
    }
}
