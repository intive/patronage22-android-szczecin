package com.intive.patronage.retro.common.api

import com.intive.patronage.retro.auth.model.service.AuthToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(var authToken: AuthToken) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder().addHeader("authorization", "Bearer ${authToken.getToken().value}").build()
        return chain.proceed(req)
    }
}
