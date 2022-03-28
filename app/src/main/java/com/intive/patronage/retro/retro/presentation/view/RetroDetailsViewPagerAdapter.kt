package com.intive.patronage.retro.retro.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.databinding.RetroDetailsCardItemLayoutBinding
import com.intive.patronage.retro.retro.presentation.entity.BoardCards

class RetroDetailsViewPagerAdapter : RecyclerView.Adapter<RetroDetailsViewPagerAdapter.RetroDetailsViewHolder>() {

    private var oldBoardCardsList = emptyList<BoardCards>()

    inner class RetroDetailsViewHolder(val binding: RetroDetailsCardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: BoardCards) {
            binding.retroDetailsTextCard.text = data.cardText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetroDetailsViewHolder {
        return RetroDetailsViewHolder(
            RetroDetailsCardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RetroDetailsViewHolder, position: Int) {
        val details = oldBoardCardsList[position]
        holder.bindItem(details)
    }

    override fun getItemCount() = oldBoardCardsList.size

    fun setBoardCardsData(newBoardCardsList: List<BoardCards>) {
        oldBoardCardsList = newBoardCardsList
    }
}
