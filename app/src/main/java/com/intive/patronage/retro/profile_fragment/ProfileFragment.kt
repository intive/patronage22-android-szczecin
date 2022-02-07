package com.intive.patronage.retro.profile_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.intive.patronage.retro.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProfileFragmentBinding.inflate(inflater, container, false).root
}
