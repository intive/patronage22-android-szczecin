package com.intive.patronage.retro.retro.model.entity

data class BoardCardsRemote(
    val id: Int,
    val cardText: String,
    val columnId: Int,
    val boardCardCreator: String,
    val actionTexts: List<String>,
    val votes: Int,
    val userVotes: Int,
)
