package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.retro.model.fakeApi.FakeRetroApi
import com.intive.patronage.retro.retro.presentation.entity.Columns
import com.intive.patronage.retro.retro.presentation.entity.Retro
import com.intive.patronage.retro.retro.presentation.entity.User

class FakeRetroRepository(private val api: FakeRetroApi) : RetroRepository {

    override fun getRetroConfiguration() = api.sendConf().map {
        Retro(
            Board(it.board.id, it.board.state, it.board.name, it.board.maximumNumberOfVotes),
            listColumns = it.listColumns.map { it2 -> Columns(it2.name, it2.id, it2.position, it2.colour) },
            listUsers = it.listUsers.map { it3 -> User(it3.email, it3.id) }
        )
    }
}
