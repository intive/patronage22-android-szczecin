package com.intive.patronage.retro.retro.presentation.entity

data class BoardCards(
    val id: Int,
    val cardText: String,
    val columnId: Int,
    val boardCardCreator: String,
    val actionTexts: List<String>,
    var votes: Int = 0,
    val userVotes: Int = 0,
)
