package com.intive.patronage.retro.about.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.databinding.AboutUsFragmentBinding

class AboutUsFragment : Fragment() {
    private lateinit var binding: AboutUsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AboutUsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
