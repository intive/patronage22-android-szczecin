package com.intive.patronage.retro.board.model.fakeApi

import com.intive.patronage.retro.board.model.entity.BoardRemote

class FakeBoardsApi {
    fun sendBoards() = listOf(
        BoardRemote(1, "CREATED", "nameOne", 4),
        BoardRemote(2, "VOTING", "nameTwo", 3),
        BoardRemote(3, "ACTIONS", "nameThree", 2)
    )
}
