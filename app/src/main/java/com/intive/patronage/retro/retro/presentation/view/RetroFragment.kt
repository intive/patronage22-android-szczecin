package com.intive.patronage.retro.retro.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.intive.patronage.retro.R
import com.intive.patronage.retro.databinding.RetroFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity

class RetroFragment : Fragment() {
    private lateinit var binding: RetroFragmentBinding
    private val args: RetroFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RetroFragmentBinding.inflate(inflater, container, false)
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()
        fab.setOnClickListener {
        }
        val boardTitle = "Board id: " + args.boardId.toString()
        binding.retroScreenTitle.text = boardTitle

        return binding.root
    }
}
