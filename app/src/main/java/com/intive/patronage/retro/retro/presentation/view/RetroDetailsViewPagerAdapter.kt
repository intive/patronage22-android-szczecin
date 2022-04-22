package com.intive.patronage.retro.retro.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.databinding.RetroDetailsCardItemLayoutBinding
import com.intive.patronage.retro.retro.presentation.entity.BoardCards

class RetroDetailsViewPagerAdapter : RecyclerView.Adapter<RetroDetailsViewPagerAdapter.RetroDetailsViewHolder>() {

    private var oldBoardCardsList = emptyList<BoardCards>()

    inner class RetroDetailsViewHolder(val binding: RetroDetailsCardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: BoardCards) {
            binding.retroDetailsTextCard.text = data.cardText
        }

        fun addVote(data: BoardCards) {
            binding.voteRetroIcon.setOnClickListener {
                // TODO this will be replaced with call to Api
                Toast.makeText(it.context, "Vote given for ${data.id}", Toast.LENGTH_SHORT).show()
            }
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
        holder.addVote(details)
    }

    override fun getItemCount() = oldBoardCardsList.size

    fun setBoardCardsData(newBoardCardsList: List<BoardCards>) {
        oldBoardCardsList = newBoardCardsList
    }
}
