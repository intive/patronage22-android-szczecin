package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.entity.BoardRemote
import com.intive.patronage.retro.board.presentation.entity.Board
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoardApi {
    @GET("api/v1/boards")
    suspend fun getBoards(): List<BoardRemote>
    @POST("/api/v1/boards")
    suspend fun addBoard(@Body board: Board): BoardRemote
}
