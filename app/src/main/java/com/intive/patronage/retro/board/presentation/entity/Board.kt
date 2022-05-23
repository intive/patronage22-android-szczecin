package com.intive.patronage.retro.board.presentation.entity

data class Board(val id: Int, val status: String, val name: String, val numberOfVotes: Int = 3)
