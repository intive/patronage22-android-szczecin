package com.intive.patronage.retro.retro.presentation.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.retro.retro.presentation.entity.Columns

class RetroColumnsDiffUtil(
    private val oldColumnsList: List<Columns>,
    private val newColumnsList: List<Columns>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldColumnsList.size

    override fun getNewListSize() = newColumnsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldColumnsList[oldItemPosition].id == newColumnsList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldColumnsList[oldItemPosition].id != newColumnsList[newItemPosition].id -> {
                false
            }
            oldColumnsList[oldItemPosition].name != newColumnsList[newItemPosition].name -> {
                false
            }
            oldColumnsList[oldItemPosition].colour != newColumnsList[newItemPosition].colour -> {
                false
            }
            else -> true
        }
    }
}
