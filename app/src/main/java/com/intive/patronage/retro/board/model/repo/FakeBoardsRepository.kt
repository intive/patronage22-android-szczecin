package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.model.fakeApi.FakeBoardsApi
import com.intive.patronage.retro.board.presentation.entity.Board

class FakeBoardsRepository(private val api: FakeBoardsApi) : BoardRepository {

    override fun getBoards() = api.sendBoards().map { Board(it.id, it.name, it.status) }
}
