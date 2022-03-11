package com.intive.patronage.retro.common.httpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HTTPClient(interceptor: Interceptor) {
    private val baseURL = "https://patronage22-szczecin-java.herokuapp.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(interceptor.getInterceptor())
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getClient() = retrofit
}
