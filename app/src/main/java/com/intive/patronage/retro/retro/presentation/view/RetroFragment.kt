package com.intive.patronage.retro.retro.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private lateinit var columns: List<Int>

    private val args: RetroFragmentArgs by navArgs()
    private val retroViewModel: RetroViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RetroFragmentBinding.inflate(inflater, container, false)
        tab = binding.tabLayoutRetro
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton
        val retroColumnsAdapter = RetroViewPagerAdapter()

        viewPager = binding.viewPagerRetro
        tab = binding.tabLayoutRetro

        viewPager.adapter = retroColumnsAdapter

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()
        fab.setOnClickListener {
            findNavController().navigate(
                RetroFragmentDirections
                    .actionRetroFragmentToRetroDialogFragment(args.boardId, columns[tab.selectedTabPosition])
            )
        }

        setViewPagerHeartBeat(retroColumnsAdapter)
        return binding.root
    }

    private fun setViewPagerHeartBeat(adapter: RetroViewPagerAdapter) {
        retroViewModel.startHeartBeatRetro(args.boardId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    retroViewModel.retroConfiguration(args.boardId).observe(viewLifecycleOwner) { it2 ->
                        when (it2.status) {
                            Status.SUCCESS -> {
                                binding.retroSpinner.visibility = View.GONE

                                adapter.setRetroColumnsData(it2.data!!, it.data!!)
                                columns = it2.data.map { list -> list.id }.toMutableList()

                                TabLayoutMediator(
                                    tab, viewPager
                                ) { tab: TabLayout.Tab, position: Int ->
                                    tab.text = it2.data[position].name
                                }.attach()
                            }
                            Status.ERROR -> {
                                retroViewModel.stopHeartBeat()
                                binding.retroSpinner.visibility = View.GONE
                                Snackbar.make(binding.retroConstraintLayout, it2.message!!, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    binding.retroSpinner.visibility = View.GONE
                    Snackbar.make(binding.retroConstraintLayout, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> binding.retroSpinner.isShown
            }
        }
    }

    private fun setViewPager(adapter: RetroViewPagerAdapter) {
        retroViewModel.retroDetails(args.boardId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    retroViewModel.retroConfiguration(args.boardId).observe(viewLifecycleOwner) { it2 ->
                        when (it2.status) {
                            Status.SUCCESS -> {
                                binding.retroSpinner.visibility = View.GONE
                                adapter.setRetroColumnsData(it2.data!!, it.data!!)
                                columns = it2.data.map { list -> list.id }.toMutableList()

                                TabLayoutMediator(
                                    tab, viewPager
                                ) { tab: TabLayout.Tab, position: Int ->
                                    tab.text = it2.data[position].name
                                }.attach()
                            }
                            Status.ERROR -> {
                                binding.retroSpinner.visibility = View.GONE
                                Snackbar.make(binding.retroConstraintLayout, it2.message!!, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
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
