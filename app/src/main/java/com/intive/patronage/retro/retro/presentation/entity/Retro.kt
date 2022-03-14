package com.intive.patronage.retro.retro.presentation.entity

import com.intive.patronage.retro.board.presentation.entity.Board

data class Retro(val board: Board, val listColumns: List<Columns>, val listUsers: List<User>)
