package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.entity.BoardRemote
import com.intive.patronage.retro.board.model.entity.BoardUpdateRemote
import com.intive.patronage.retro.board.presentation.entity.Board
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardApi {
    @GET("api/v1/boards")
    suspend fun getBoards(): List<BoardRemote>

    @POST("api/v1/boards")
    suspend fun addBoard(@Body board: Board): BoardRemote

    @POST("api/v1/boards/{id}/users")
    suspend fun addUsers(@Path("id") id: Int, @Body users: List<String>): List<String>

    @POST("/api/v1/boards/{id}/nextState")
    suspend fun changeState(@Path("id") id: Int)

    @DELETE("api/v1/boards/{id}/")
    suspend fun deleteBoard(@Path("id") id: Int)

    @PATCH("api/v1/boards/{id}/")
    suspend fun updateBoard(@Path("id") id: Int, @Body boardUpdateRemote: BoardUpdateRemote)

    @GET("api/v1/users")
    suspend fun getUsers(@Query("email") email: String): List<String>
}
