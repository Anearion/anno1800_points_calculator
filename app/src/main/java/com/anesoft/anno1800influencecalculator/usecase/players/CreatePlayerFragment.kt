package com.anesoft.anno1800influencecalculator.usecase.players

import android.os.Bundle
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentCreatePlayerBinding

class CreatePlayerFragment : BaseFragment<FragmentCreatePlayerBinding, PlayersViewModel>() {

    override fun getViewModelClass(): Class<PlayersViewModel> {
        return PlayersViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {

    }


}