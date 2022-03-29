package com.intive.patronage.retro.retro.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.intive.patronage.retro.board.model.entity.BoardRemote

data class RetroRemote(

    val board: BoardRemote,
    @SerializedName("columns")
    @Expose
    val listColumns: List<ColumnsRemote>,
    @SerializedName("users")
    @Expose
    val listUsers: List<UserRemote>,
)
