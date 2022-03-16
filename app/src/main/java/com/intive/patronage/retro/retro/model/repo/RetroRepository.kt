package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.ResponseHandler
import com.intive.patronage.retro.retro.presentation.entity.Columns

class RetroRepository(private val api: RetroApiImpl, private var responseHandler: ResponseHandler) {

    suspend fun getRetroConfiguration(id: Int): Resource<List<Columns>> {
        return try {
            responseHandler.handleSuccess(
                api.getRetroApi().getRetroConfiguration(id).listColumns.map
                { Columns(it.name, it.id, it.position, it.colour) }
            )
        } catch (e: Exception) {
            responseHandler.handleException()
        }
    }
}
