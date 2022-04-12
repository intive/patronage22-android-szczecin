package com.intive.patronage.retro.retro.presentation.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.databinding.ViewRetroHolderItemBinding
import com.intive.patronage.retro.retro.presentation.entity.Columns
import com.intive.patronage.retro.retro.presentation.entity.RetroDetails

class RetroViewPagerAdapter : RecyclerView.Adapter<RetroViewPagerAdapter.ViewHolder>() {

    private var oldColumnsList = emptyList<Columns>()
    private var oldRetroDetailsList = emptyList<RetroDetails>()
    private val adapter = RetroDetailsViewPagerAdapter()

    inner class ViewHolder(val binding: ViewRetroHolderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(color: Columns, details: RetroDetails) {
            binding.retroDetailsCardRecyclerView.adapter = adapter
            binding.viewRetroHolderItem.setBackgroundColor(Color.parseColor(color.colour))
            adapter.setBoardCardsData(details.boardCards)
        }
    }

    override fun getItemCount(): Int = oldColumnsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(oldColumnsList[position], oldRetroDetailsList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewRetroHolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun setRetroColumnsData(newColumnsList: List<Columns>, newRetroDetailsList: List<RetroDetails>) {
        val diffUtil = RetroColumnsDiffUtil(oldColumnsList, newColumnsList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)

        val diffUtil2 = RetroDetailsDiffUtil(oldRetroDetailsList, newRetroDetailsList)
        val diffResult2 = DiffUtil.calculateDiff(diffUtil2)

        oldRetroDetailsList = newRetroDetailsList
        oldColumnsList = newColumnsList
        diffResult2.dispatchUpdatesTo(this)
        diffResults.dispatchUpdatesTo(this)
    }
}
