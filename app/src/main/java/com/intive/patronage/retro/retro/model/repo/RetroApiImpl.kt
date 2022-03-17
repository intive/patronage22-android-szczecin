package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.common.api.RestClient

class RetroApiImpl(private val retrofit: RestClient) {

    fun getRetroApi(): RetroApi = retrofit.buildRetrofit().create(RetroApi::class.java)
}
