package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.retro.model.entity.BoardCardsRemote
import com.intive.patronage.retro.retro.model.entity.RetroDetailsRemote
import com.intive.patronage.retro.retro.model.entity.RetroRemote
import com.intive.patronage.retro.retro.presentation.entity.BoardCards
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroApi {

    @GET("api/v1/boards/{id}")
    suspend fun getRetroConfiguration(@Path("id") id: Int): RetroRemote

    @GET("api/v1/boards/{id}/details")
    suspend fun getRetroDetails(@Path("id") id: Int): List<RetroDetailsRemote>

    @POST("api/v1/cards/boards/{id}")
    suspend fun postCard(@Path("id") id: Int, @Body card: BoardCards): BoardCardsRemote

    @POST("api/v1/cards/{id}/votes")
    suspend fun postVote(@Path("id") id: Int)
}
