package com.intive.patronage.retro.board.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.databinding.BoardRecyclerItemLayoutBinding

class BoardRecyclerAdapter : RecyclerView.Adapter<BoardRecyclerAdapter.BoardViewHolder>() {

    private var oldBoardList = emptyList<Board>()
    private lateinit var oldViewModel: BoardViewModel
    private var boardFlag: Boolean = false
    private var boardPosition: Int? = null

    inner class BoardViewHolder(val binding: BoardRecyclerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

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

        fun deleteBoard(board: Board, position: Int) {
            binding.boardViewModel = oldViewModel
            binding.board = board
            binding.boardPosition = position
            if (boardFlag && boardPosition == position) {
                binding.indicatorBoardsItem.visibility = View.VISIBLE
            } else {
                binding.indicatorBoardsItem.visibility = View.GONE
            }
        }

        fun editBoard(board: Board) {
            binding.editBoardIcon.setOnClickListener {
                Navigation.findNavController(it).navigate(BoardsFragmentDirections.actionBoardsFragmentToAddBoardDialog(board.id))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardRecyclerAdapter.BoardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BoardRecyclerItemLayoutBinding.inflate(layoutInflater, parent, false)
        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board = oldBoardList[position]
        holder.bindItem(board)
        holder.setAction(board)
        holder.setAddUser(board)
        holder.deleteBoard(board, position)
        holder.editBoard(board)
    }

    override fun getItemCount() = oldBoardList.size

    @SuppressLint("NotifyDataSetChanged")
    fun uploadBoardsData(newBoardList: List<Board>, newViewModel: BoardViewModel) {
        oldBoardList = newBoardList
        oldViewModel = newViewModel
        notifyDataSetChanged()
    }

    fun recyclerAdapterProgressVisibility(flag: Boolean, position: Int) {
        boardFlag = flag
        boardPosition = position
        notifyItemChanged(position)
    }
}
