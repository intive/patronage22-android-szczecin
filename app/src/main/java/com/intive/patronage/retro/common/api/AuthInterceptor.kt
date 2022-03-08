package com.intive.patronage.retro.common.api

import com.intive.patronage.retro.common.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder().addHeader("authorization", "Bearer ${Constants.token}").build()
        return chain.proceed(req)
    }
}
