package com.anesoft.anno1800influencecalculator.states.games

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.anesoft.anno1800influencecalculator.base.BaseViewBindingFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : BaseViewBindingFragment<FragmentGameBinding, GameViewModel>() {

    val args : GameFragmentArgs by navArgs()

    override fun getViewModelClass(): Class<GameViewModel> {
        return GameViewModel::class.java
    }

    override fun onViewCreation(savedInstanceState: Bundle?) {
        viewModel.getGameScores(args.gameId)
        viewModel.allScores.observe(viewLifecycleOwner){
            Toast.makeText(context, "Number of players: "+it.size, Toast.LENGTH_SHORT).show()
        }
    }

}