package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.intive.patronage.retro.R
import com.intive.patronage.retro.databinding.BoardFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity

class BoardsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = BoardFragmentBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomAppBar = (activity as MainActivity).findViewById<BottomAppBar>(R.id.bottomAppBar)
        val fab = (activity as MainActivity).findViewById<FloatingActionButton>(R.id.floatingButton)

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()
    }
}
