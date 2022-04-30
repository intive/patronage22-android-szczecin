package com.intive.patronage.retro.retro.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.databinding.RetroDetailsCardItemLayoutBinding
import com.intive.patronage.retro.retro.presentation.entity.BoardCards
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel

const val MAX_CARD_VOTES = 1

class RetroDetailsViewPagerAdapter : RecyclerView.Adapter<RetroDetailsViewPagerAdapter.RetroDetailsViewHolder>() {

    private var oldBoardCardsList = emptyList<BoardCards>()
    private lateinit var viewModel: RetroViewModel

    inner class RetroDetailsViewHolder(val binding: RetroDetailsCardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: BoardCards) {
            if (data.votes >= MAX_CARD_VOTES) binding.voteRetroIcon.visibility = View.GONE
            binding.retroDetailsTextCard.text = data.cardText
        }

        fun addVote(data: BoardCards) {
            binding.viewModel = viewModel
            binding.card = data
            // TODO databinding will be used for userVotes counter

            binding.voteRetroIcon.setOnClickListener {
                viewModel.vote(data.id)
                if (data.votes >= MAX_CARD_VOTES) {
                    binding.voteRetroIcon.visibility = View.GONE
                }
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

    fun setBoardCardsData(newBoardCardsList: List<BoardCards>, newViewModel: RetroViewModel) {
        oldBoardCardsList = newBoardCardsList
        viewModel = newViewModel
    }
}
