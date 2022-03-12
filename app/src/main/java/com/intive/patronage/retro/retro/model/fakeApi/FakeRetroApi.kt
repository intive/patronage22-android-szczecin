package com.intive.patronage.retro.retro.model.fakeApi

import com.intive.patronage.retro.board.model.entity.BoardRemote
import com.intive.patronage.retro.retro.model.entity.ColumnsRemote
import com.intive.patronage.retro.retro.model.entity.RetroRemote
import com.intive.patronage.retro.retro.model.entity.UserRemote

class FakeRetroApi {
    fun sendConf() = listOf(
        RetroRemote(
            BoardRemote(1, "CREATED", "nameOne", 4),
            listOf(
                ColumnsRemote("What wnet well", 123, 2, "#ff9100"),
                ColumnsRemote("Test well well well well well well well well well well well", 124, 2, "#ff1200"),
                ColumnsRemote(" well", 125, 2, "#006eff")
            ),
            listOf(
                UserRemote("test@gmail.com", "23")
            )
        )
    )
}
