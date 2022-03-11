package com.intive.patronage.retro.board.model.service

import com.intive.patronage.retro.board.model.repo.BoardAPI
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.common.httpClient.HTTPClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBoard(private val httpClient: HTTPClient) {

    fun addBoard(name: String, onSuccess: () -> Unit, onError: (name: String) -> Unit) {

        val httpService = httpClient.getClient().create(BoardAPI::class.java)
        val call = httpService.addBoard(Board(id = 0, status = "", name = name, numberOfVotes = 0))

        call.enqueue(
            object : Callback<Board> {
                override fun onResponse(call: Call<Board>, response: Response<Board>) {
                    if (response.code() == 201) {
                        onSuccess()
                    } else {
                        onError("Something went wrong")
                    }
                }
                override fun onFailure(call: Call<Board>, t: Throwable) {
                    onError("Something went wrong")
                }
            }
        )
    }
}
