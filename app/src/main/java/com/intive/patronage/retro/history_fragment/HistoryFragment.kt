package com.intive.patronage.retro.history_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.databinding.HistoryFragmentBinding

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = HistoryFragmentBinding.inflate(inflater, container, false).root
}
