package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.presentation.entity.Board

class BoardRepository(private val api: BoardApi) {

    suspend fun getBoards() = api.getBoards().map { Board(it.id, it.state, it.name, it.numberOfVotes) }
}
