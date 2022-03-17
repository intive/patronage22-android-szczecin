package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.retro.model.entity.RetroRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroApi {

    @GET("api/v1/boards/{id}")
    suspend fun getRetroConfiguration(@Path("id") id: Int): RetroRemote
}
