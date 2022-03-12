package com.intive.patronage.retro.retro.model.entity

import com.intive.patronage.retro.board.model.entity.BoardRemote

data class RetroRemote(val board: BoardRemote, val listColumns: List<ColumnsRemote>, val listUsers: List<UserRemote>)
