package com.intive.patronage.retro.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.MainActivity
import com.intive.patronage.retro.databinding.ProfileFragmentBinding
import com.intive.patronage.retro.firebase.FirebaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val firebaseViewModel: FirebaseViewModel by viewModel()
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogOut.setOnClickListener {
            (activity as MainActivity).signIn()
            firebaseViewModel.logOut()
        }
    }
}
