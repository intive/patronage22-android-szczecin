package com.intive.patronage.retro.about.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.about.presentation.viewModel.AboutUsViewModel
import com.intive.patronage.retro.databinding.AboutUsFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutUsFragment : Fragment() {
    private lateinit var binding: AboutUsFragmentBinding
    private val aboutUsViewModel: AboutUsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AboutUsFragmentBinding.inflate(inflater, container, false)
        val fab = (activity as MainActivity).binding.floatingButton
        fab.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutUsViewModel.downloadDevs()
        aboutUsViewModel.areDevsLoaded.observe(viewLifecycleOwner) { areDevsLoaded ->
            if (areDevsLoaded) {
                binding.aboutUsRecyclerView.adapter = AboutUsRecyclerAdapter(aboutUsViewModel.getDevs(), binding.progressBarCircular)
            } else {
                binding.progressBarCircular.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.progressBarCircular.visibility = View.VISIBLE
    }
}
