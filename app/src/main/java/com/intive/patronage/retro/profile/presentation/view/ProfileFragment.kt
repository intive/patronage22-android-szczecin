package com.intive.patronage.retro.profile.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.intive.patronage.retro.R
import com.intive.patronage.retro.common.binding.loadImage
import com.intive.patronage.retro.databinding.ProfileFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModel()
    lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton
        setupBottomBar(bottomAppBar, fab)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.userAvatarImage.loadImage(mainViewModel.getPicUri())
        binding.userNameText.text = mainViewModel.getDisplayName()
        binding.userEmailText.text = mainViewModel.getEmail()
    }

    private fun setupBottomBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {

        fab.hide()
        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_profile)

        bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_log_out -> {
                    (activity as MainActivity).signIn()
                    mainViewModel.logOut()
                    true
                }
                else -> false
            }
        }
    }
}
