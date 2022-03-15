package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.common.api.RestClient

class BoardApiImpl(private val retrofit: RestClient) {

    fun getBoardApi(): BoardApi = retrofit.buildRetrofit().create(BoardApi::class.java)
}
