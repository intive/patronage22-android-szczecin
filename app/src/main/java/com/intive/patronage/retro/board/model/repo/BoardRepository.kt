package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.entity.BoardRemote
import com.intive.patronage.retro.board.model.entity.BoardUpdateRemote
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

    suspend fun addBoard(board: Board): Resource<BoardRemote> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().addBoard(board))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun addUsers(id: Int, users: List<String>): Resource<List<String>> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().addUsers(id, users))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun changeState(id: Int): Resource<Unit> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().changeState(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun deleteBoard(id: Int): Resource<Unit> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().deleteBoard(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun updateBoard(id: Int, boardUpdate: BoardUpdateRemote): Resource<Unit> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().updateBoard(id, boardUpdate))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getUsers(email: String): Resource<List<String>> {
        return try {
            responseHandler.handleSuccess(api.getBoardApi().getUsers(email))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
