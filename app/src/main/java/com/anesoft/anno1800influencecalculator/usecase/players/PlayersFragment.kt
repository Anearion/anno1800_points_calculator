package com.anesoft.anno1800influencecalculator.usecase.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayersBinding

class PlayersFragment : BaseFragment<FragmentPlayersBinding>(R.layout.fragment_players) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPlayersBinding
        get() = FragmentPlayersBinding::inflate

    override fun setup() {
        binding.title.text = "post init"
    }
}