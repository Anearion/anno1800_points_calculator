package com.anesoft.anno1800influencecalculator.usecase.games

import android.os.Bundle
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding, GameViewModel>() {

    override fun getViewModelClass(): Class<GameViewModel> {
        return GameViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {

    }

}