package com.intive.patronage.retro.common.api

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> = Resource.success(data)

    fun <T : Any> handleException(): Resource<T> = Resource.error("Something went wrong", null)
}
