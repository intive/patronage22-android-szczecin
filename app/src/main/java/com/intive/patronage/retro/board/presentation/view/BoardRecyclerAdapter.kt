package com.intive.patronage.retro.board.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.databinding.BoardRecyclerItemLayoutBinding

class BoardRecyclerAdapter(private val boardList: List<Board>) : RecyclerView.Adapter<BoardRecyclerAdapter.BoardViewHolder>() {
    inner class BoardViewHolder(private val binding: BoardRecyclerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(board: Board) {
            binding.textBoardName.text = board.name
            binding.textBoardStatus.text = board.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        return BoardViewHolder(BoardRecyclerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board = boardList[position]
        holder.bindItem(board)
    }

    override fun getItemCount() = boardList.size
}
