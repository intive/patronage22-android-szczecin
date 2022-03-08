package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.entity.BoardRemote
import retrofit2.http.GET

interface BoardApi {
    @GET("api/v1/boards")
    suspend fun getBoards(): List<BoardRemote>
}
