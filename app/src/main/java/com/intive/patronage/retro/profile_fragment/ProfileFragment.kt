package com.intive.patronage.retro.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.intive.patronage.retro.MainActivity
import com.intive.patronage.retro.R
import com.intive.patronage.retro.databinding.ProfileFragmentBinding
import com.intive.patronage.retro.firebase.FirebaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val firebaseViewModel: FirebaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProfileFragmentBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomAppBar = (activity as MainActivity).findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_profile)

        bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_log_out -> {
                    (activity as MainActivity).signIn()
                    firebaseViewModel.logOut()
                    true
                }
                else -> false
            }
        }
    }
}
