package com.intive.patronage.retro.retro.model.repo

import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.ResponseHandler
import com.intive.patronage.retro.retro.model.entity.BoardCardsRemote
import com.intive.patronage.retro.retro.presentation.entity.BoardCards
import com.intive.patronage.retro.retro.presentation.entity.Columns
import com.intive.patronage.retro.retro.presentation.entity.RetroDetails

class RetroRepository(private val api: RetroApiImpl, private var responseHandler: ResponseHandler) {

    suspend fun getRetroConfiguration(id: Int): Resource<List<Columns>> {
        return try {
            responseHandler.handleSuccess(
                api.getRetroApi().getRetroConfiguration(id).listColumns.map
                { Columns(it.name, it.id, it.position, it.colour) }
            )
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getRetroDetails(id: Int): Resource<List<RetroDetails>> {
        return try {
            responseHandler.handleSuccess(
                api.getRetroApi().getRetroDetails(id).map {
                    RetroDetails(
                        it.id,
                        boardCards = it.boardCards.map { it2 ->
                            BoardCards(
                                it2.id, it2.cardText, it2.columnId, it2.boardCardCreator, it2.actionTexts, it2.votes, it2.userVotes
                            )
                        }
                    )
                }
            )
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun postCard(id: Int, card: BoardCards): Resource<BoardCardsRemote> {
        return try {
            responseHandler.handleSuccess(api.getRetroApi().postCard(id, card))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun postVote(id: Int): Resource<Unit> {
        return try {
            responseHandler.handleSuccess(api.getRetroApi().postVote(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
