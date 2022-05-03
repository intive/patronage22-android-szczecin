package com.intive.patronage.retro.retro.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intive.patronage.retro.R
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.databinding.RetroFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import com.intive.patronage.retro.retro.presentation.entity.Columns
import com.intive.patronage.retro.retro.presentation.entity.RetroDetails
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetroFragment : Fragment() {
    private lateinit var binding: RetroFragmentBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tab: TabLayout
    private lateinit var columns: List<Int>

    private val args: RetroFragmentArgs by navArgs()
    private val retroViewModel: RetroViewModel by viewModel()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val navigator: RetroNavigator by inject()
    private lateinit var startColumns: List<Columns>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator.attach(this)
    }

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
        fab.hide()
        fab.setOnClickListener {
            findNavController().navigate(
                RetroFragmentDirections
                    .actionRetroFragmentToRetroDialogFragment(args.boardId, columns[tab.selectedTabPosition])
            )
        }
        refreshCards(retroColumnsAdapter)
        setViewPagerHeartBeat(retroColumnsAdapter, fab)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detach(this)
    }

    private fun refreshCards(retroColumnsAdapter: RetroViewPagerAdapter) {
        mainViewModel.isDialogClosed.observe(viewLifecycleOwner) {
            if (it) {
                refreshPagerAdapter(retroColumnsAdapter)
                mainViewModel.isDialogClosed.value = false
            }
        }
    }

    private fun setViewPagerHeartBeat(adapter: RetroViewPagerAdapter, fab: FloatingActionButton) {
        retroViewModel.retroConfiguration(args.boardId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    TabLayoutMediator(
                        tab, viewPager
                    ) { tab: TabLayout.Tab, position: Int ->
                        tab.text = it.data!![position].name
                    }.attach()

                    retroViewModel.startHeartBeatRetro(args.boardId).observe(viewLifecycleOwner) { it2 ->
                        when (it2.status) {
                            Status.SUCCESS -> {
                                fab.show()
                                binding.retroSpinner.visibility = View.GONE
                                binding.errorViewPagerCards.root.visibility = View.GONE
                                binding.viewPagerRetro.visibility = View.VISIBLE
                                startColumns = it.data!!
                                adapter.setRetroColumnsData(it.data, it2.data!!, retroViewModel)
                                columns = it.data.map { list -> list.id }.toMutableList()
                            }
                            Status.ERROR -> {
                                fab.hide()
                                adapter.setRetroColumnsData(it.data!!, emptyBoardCardsList(it.data.size), retroViewModel)
                                columns = it.data.map { list -> list.id }.toMutableList()
                                binding.viewPagerRetro.visibility = View.GONE
                                binding.retroSpinner.visibility = View.GONE
                                binding.errorViewPagerCards.root.visibility = View.VISIBLE
                            }
                            else -> {}
                        }
                    }
                }
                Status.ERROR -> {
                    retroViewModel.stopHeartBeat()
                    binding.retroSpinner.visibility = View.GONE
                    Snackbar.make(binding.retroConstraintLayout, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> binding.retroSpinner.isShown
            }
        }
    }

    private fun refreshPagerAdapter(adapter: RetroViewPagerAdapter) {
        binding.horizontalProgressBar.visibility = View.VISIBLE
        retroViewModel.retroDetailsRefresh(args.boardId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.horizontalProgressBar.visibility = View.GONE
                    adapter.setRetroColumnsData(startColumns, it.data!!, retroViewModel)
                }
                Status.ERROR -> {
                    adapter.setRetroColumnsData(startColumns, emptyBoardCardsList(it.data!!.size), retroViewModel)
                    columns = it.data.map { list -> list.id }.toMutableList()
                    binding.viewPagerRetro.visibility = View.GONE
                    binding.retroSpinner.visibility = View.GONE
                    binding.errorViewPagerCards.root.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.horizontalProgressBar.isShown
                }
            }
        }
    }

    private fun emptyBoardCardsList(countColumns: Int): MutableList<RetroDetails> {
        val emptyBoardCardsList = emptyList<RetroDetails>().toMutableList()
        for (i in 0..countColumns) {
            emptyBoardCardsList += RetroDetails(i, emptyList())
        }
        return emptyBoardCardsList
    }
}
