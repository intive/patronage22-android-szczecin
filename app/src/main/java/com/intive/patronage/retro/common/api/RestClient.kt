package com.intive.patronage.retro.common.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestClient(private val okHttpClient: HttpClient, private val baseUrl: String) {

    fun buildRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient.client())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}
