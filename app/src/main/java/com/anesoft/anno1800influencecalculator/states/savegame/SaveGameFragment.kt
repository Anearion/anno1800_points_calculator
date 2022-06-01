package com.anesoft.anno1800influencecalculator.states.savegame

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anesoft.anno1800influencecalculator.base.BaseViewBindingFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentSaveGameBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.uicomponents.SelectPlayersBottomSheet
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.OnAdapterItemClick
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.PlayerWithScoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveGameFragment : BaseViewBindingFragment<FragmentSaveGameBinding, SaveGameViewModel>() {

    private lateinit var adapter: PlayerWithScoreAdapter
    override var useSharedViewModel = true

    override fun getViewModelClass(): Class<SaveGameViewModel> {
        return SaveGameViewModel::class.java
    }

    override fun onViewCreation(savedInstanceState: Bundle?) {

        val listener  = object : OnAdapterItemClick<SaveGameViewModel.PlayerWithScore> {
            override fun onClick(playerWithScore: SaveGameViewModel.PlayerWithScore) {
                val action = SaveGameFragmentDirections.actionNavigationSaveGameToNavigationPlayerScoreBottomsheet(playerId = playerWithScore.player.id, gameId = viewModel.gameId)
                findNavController().navigate(action)
            }
        }

        adapter = PlayerWithScoreAdapter()
        adapter.enableOnClickListener(listener)
        binding.rvSelectedPlayers.adapter = adapter

        binding.rvSelectedPlayers.layoutManager = GridLayoutManager(context, 2)

        setFragmentResultListener(SelectPlayersBottomSheet.REQUEST_KEY) { key, bundle ->
            val list = bundle["data"] as List<String>
            viewModel.getPlayerByName(list)
        }

        viewModel.playersLiveData.observe(viewLifecycleOwner) { it ->
            it.forEach {
                val score = Score(gameId = viewModel.gameId, playerId = it.id)
                viewModel.saveScore(score)
            }
        }

        viewModel.playersWithScoreLiveData.observe(viewLifecycleOwner) { it ->
            adapter.updateDataset(it)
        }

        binding.btSelectPlayer.setOnClickListener {



            val directionToSelectPlayerBottomsheet =
                SaveGameFragmentDirections.directionToSelectPlayerBottomsheet()
            findNavController().navigate(directionToSelectPlayerBottomsheet)

        }

        binding.deleteAllButton.setOnClickListener { v -> viewModel.deleteAll() }

    }

    override fun onResumeUpdate() {
        super.onResumeUpdate()

    }
}