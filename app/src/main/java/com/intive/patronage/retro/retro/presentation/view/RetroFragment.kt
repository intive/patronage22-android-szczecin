package com.intive.patronage.retro.retro.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intive.patronage.retro.R
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.databinding.RetroFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetroFragment : Fragment() {
    private lateinit var binding: RetroFragmentBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tab: TabLayout

    private val args: RetroFragmentArgs by navArgs()
    private val retroViewModel: RetroViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RetroFragmentBinding.inflate(inflater, container, false)

        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()
        fab.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(RetroFragmentDirections.actionRetroFragmentToRetroDialogFragment())
        }
        setViewPager()

        return binding.root
    }

    private fun setViewPager() {

        retroViewModel.retroConfiguration(args.boardId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.retroSpinner.visibility = View.GONE
                    viewPager = binding.viewPagerRetro
                    tab = binding.tabLayoutRetro

                    viewPager.adapter = RetroViewPagerAdapter(it.data!!)

                    TabLayoutMediator(
                        tab, viewPager
                    ) { tab: TabLayout.Tab, position: Int ->
                        tab.text = it.data[position].name
                    }.attach()
                }
                Status.ERROR -> {
                    binding.retroSpinner.visibility = View.GONE
                    Snackbar.make(binding.retroConstraintLayout, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> binding.retroSpinner.isShown
            }
        }
    }
}
