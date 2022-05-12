package com.anesoft.anno1800influencecalculator.states.players

import android.os.Bundle
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayerDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerDetailFragment : BaseFragment<FragmentPlayerDetailsBinding, PlayersViewModel>() {

    override fun getViewModelClass(): Class<PlayersViewModel> {
        return PlayersViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}