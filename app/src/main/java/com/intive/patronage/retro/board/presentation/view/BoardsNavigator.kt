package com.intive.patronage.retro.board.presentation.view

import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.retro.R

class BoardsNavigator {

    var fragment: BoardsFragment? = null

    fun attach(fragment: BoardsFragment) {
        this.fragment = fragment
    }

    fun detach(fragment: BoardsFragment) {
        if (this.fragment === fragment)
            this.fragment = null
    }

    fun navigateToBoards() {
        fragment!!.findNavController().navigate(R.id.action_boardsFragment_self)
    }

    fun recyclerAdapterProgress(flag: Boolean, boardPosition: Int) {
        fragment!!.binding.boardRecyclerAdapter!!.recyclerAdapterProgressVisibility(flag, boardPosition)
    }

    fun errorSnackBar(message: String) {
        Snackbar.make(fragment!!.requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}
