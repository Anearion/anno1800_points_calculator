package com.anesoft.anno1800influencecalculator.usecase.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayersBinding

class PlayersFragment : BaseFragment<FragmentPlayersBinding, PlayersViewModel>() {

    override fun update(savedInstanceState: Bundle?) {

    }

    override fun getViewModelClass(): Class<PlayersViewModel> {
        return PlayersViewModel::class.java
    }


}