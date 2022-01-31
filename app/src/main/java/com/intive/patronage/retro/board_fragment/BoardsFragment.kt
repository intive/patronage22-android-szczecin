package com.intive.patronage.retro.board_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.databinding.BoardFragmentBinding

class BoardsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = BoardFragmentBinding.inflate(inflater, container, false).root
}
