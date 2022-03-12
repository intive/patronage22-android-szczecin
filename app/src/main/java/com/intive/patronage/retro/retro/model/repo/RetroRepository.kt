package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.retro.presentation.entity.Retro

interface RetroRepository {

    fun getRetroConfiguration(): List<Retro>
}
