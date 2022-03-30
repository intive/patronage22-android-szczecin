package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.entity.BoardRemote
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.ResponseHandler

class BoardRepository(private val api: BoardApiImpl, var responseHandler: ResponseHandler) {

    suspend fun getBoards(): Resource<List<Board>> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().getBoards().map { Board(it.id, it.state, it.name, it.numberOfVotes) })
        } catch (e: Exception) {
            responseHandler.handleException()
        }
    }

    suspend fun addBoard(board: Board): Resource<BoardRemote> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().addBoard(board))
        } catch (e: Exception) {
            responseHandler.handleException()
        }
    }

    suspend fun addUsers(id: Int, users: List<String>): Resource<List<String>> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().addUsers(id, users))
        } catch (e: Exception) {
            responseHandler.handleException()
        }
    }
}
