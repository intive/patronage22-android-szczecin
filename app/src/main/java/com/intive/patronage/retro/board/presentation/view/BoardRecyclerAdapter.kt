package com.intive.patronage.retro.board.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.databinding.BoardRecyclerItemLayoutBinding

class BoardRecyclerAdapter(private val boardList: List<Board>) : RecyclerView.Adapter<BoardRecyclerAdapter.BoardViewHolder>() {
    inner class BoardViewHolder(private val binding: BoardRecyclerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(board: Board) {
            binding.textBoardName.text = board.name
            binding.textBoardStatus.text = board.status
        }

        fun setAction(board: Board) {
            binding.cardViewBoard.setOnClickListener {
                val action = BoardsFragmentDirections.actionBoardsFragmentToRetroFragment(board.id)
                Navigation.findNavController(it).navigate(action)
            }
        }

        fun setAddUser(board: Board) {
            binding.addUserIcon.setOnClickListener {
                val action = BoardsFragmentDirections.actionBoardsFragmentToAddUserDialog(board.id)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        return BoardViewHolder(BoardRecyclerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board = boardList[position]
        holder.bindItem(board)
        holder.setAction(board)
        holder.setAddUser(board)
    }

    override fun getItemCount() = boardList.size
}
