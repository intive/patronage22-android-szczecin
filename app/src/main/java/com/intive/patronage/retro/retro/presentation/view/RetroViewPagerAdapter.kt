package com.intive.patronage.retro.retro.presentation.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.databinding.ViewRetroHolderItemBinding
import com.intive.patronage.retro.retro.presentation.entity.Columns
import com.intive.patronage.retro.retro.presentation.entity.Retro

class RetroViewPagerAdapter(
    private val data: List<Retro>
) : RecyclerView.Adapter<RetroViewPagerAdapter.ViewHolder>() {

    private val dataForColor = data.flatMap { it.listColumns }

    inner class ViewHolder(val binding: ViewRetroHolderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(color: Columns) {
            binding.viewRetroHolderItem.setBackgroundColor(Color.parseColor(color.colour))
        }
    }

    override fun getItemCount(): Int = dataForColor.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dataForColor[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewRetroHolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}
