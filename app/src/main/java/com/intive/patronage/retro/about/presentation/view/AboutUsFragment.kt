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

        aboutUsViewModel.downloadDevs().observe(viewLifecycleOwner) { areDevsLoaded ->
            if (areDevsLoaded) {
                binding.devsRecyclerView.adapter = AboutUsRecyclerAdapter(aboutUsViewModel.getDevs(), binding)
            } else {
                with(binding) {
                    devsProgressBarCircular.visibility = View.GONE
                    devsErrorImage.visibility = View.VISIBLE
                    devsErrorMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.devsRecyclerView.adapter == null) {
            binding.devsProgressBarCircular.visibility = View.VISIBLE
        }
    }
}
