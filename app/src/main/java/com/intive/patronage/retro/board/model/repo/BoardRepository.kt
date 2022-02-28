package com.intive.patronage.retro.board.model.repo

import com.intive.patronage.retro.board.presentation.entity.Board

interface BoardRepository {

    fun getBoards(): List<Board>
}
