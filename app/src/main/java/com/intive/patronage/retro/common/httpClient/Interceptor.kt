package com.intive.patronage.retro.common.httpClient

import com.intive.patronage.retro.auth.model.service.Token
import okhttp3.OkHttpClient
import okhttp3.Request

class Interceptor(userToken: Token) {
    private val interceptor: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${userToken.getToken()}")
            .build()
        chain.proceed(newRequest)
    }.build()

    fun getInterceptor() = interceptor
}
