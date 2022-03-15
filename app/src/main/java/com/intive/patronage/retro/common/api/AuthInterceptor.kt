package com.intive.patronage.retro.common.api

import com.intive.patronage.retro.auth.model.service.Token
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(var userToken: Token) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder().addHeader("authorization", "Bearer ${userToken.getToken()}").build()
        return chain.proceed(req)
    }
}
