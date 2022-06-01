package com.anesoft.anno1800influencecalculator.states.players

import android.os.Bundle
import com.anesoft.anno1800influencecalculator.base.BaseViewBindingFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayerDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerDetailFragment : BaseViewBindingFragment<FragmentPlayerDetailsBinding, PlayersViewModel>() {

    override fun getViewModelClass(): Class<PlayersViewModel> {
        return PlayersViewModel::class.java
    }

    override fun onViewCreation(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}