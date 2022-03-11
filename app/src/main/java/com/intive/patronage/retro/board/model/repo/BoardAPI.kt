package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.entity.BoardRemote
import com.intive.patronage.retro.board.presentation.entity.Board
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoardAPI {
    @POST("/api/v1/boards")
    fun addBoard(@Body board: Board): Call<Board>
    @GET("/api/v1/boards")
    fun getBoards(): List<BoardRemote>
}
