package com.intive.patronage.retro.common.api

import okhttp3.OkHttpClient

class HttpClient(private val authInterceptor: AuthInterceptor) {

    fun client(): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}
