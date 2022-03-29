package com.intive.patronage.retro.retro.presentation.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.retro.retro.presentation.entity.RetroDetails

class RetroDetailsDiffUtil(
    private val oldRetroDetailsList: List<RetroDetails>,
    private val newRetroDetailsList: List<RetroDetails>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldRetroDetailsList.size

    override fun getNewListSize() = newRetroDetailsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRetroDetailsList[oldItemPosition].id == newRetroDetailsList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldRetroDetailsList[oldItemPosition].id != newRetroDetailsList[newItemPosition].id -> {
                false
            }
            oldRetroDetailsList[oldItemPosition].boardCards != newRetroDetailsList[newItemPosition].boardCards -> {
                false
            }
            else -> true
        }
    }
}
