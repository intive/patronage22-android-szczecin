package com.intive.patronage.retro.more.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.intive.patronage.retro.R
import com.intive.patronage.retro.databinding.MoreFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = MoreFragmentBinding.inflate(inflater, container, false)
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.hide()
        binding.btLicenses.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_moreFragment_to_ossLicensesMenuActivity)
        }

        return binding.root
    }
}
