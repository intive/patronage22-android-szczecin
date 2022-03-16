package com.intive.patronage.retro.common.api

import retrofit2.HttpException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> = Resource.success(data)

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            403 -> "forbidden"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}
