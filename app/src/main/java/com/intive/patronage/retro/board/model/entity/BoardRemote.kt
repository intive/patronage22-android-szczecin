package com.intive.patronage.retro.board.model.entity

data class BoardRemote(val id: Int, val state: String, val name: String, val numberOfVotes: Int = 3)
