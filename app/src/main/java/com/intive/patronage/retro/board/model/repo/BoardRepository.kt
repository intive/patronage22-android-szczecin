package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.ResponseHandler

class BoardRepository(private val api: BoardApiImpl, var responseHandler: ResponseHandler) {

    suspend fun getBoards(): Resource<List<Board>> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().getBoards().map { Board(it.id, it.state, it.name, it.numberOfVotes) })
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
