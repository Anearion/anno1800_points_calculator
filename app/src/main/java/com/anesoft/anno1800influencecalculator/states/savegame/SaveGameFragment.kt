package com.anesoft.anno1800influencecalculator.states.savegame

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentSaveGameBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.uicomponents.SelectPlayersBottomSheet
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.OnAdapterItemClick
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.PlayerWithScoreAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.random.Random

@AndroidEntryPoint
class SaveGameFragment : BaseFragment<FragmentSaveGameBinding, SaveGameViewModel>() {

    private lateinit var adapter: PlayerWithScoreAdapter

    var gameId: Int = 0
    override var useSharedViewModel = true


    override fun getViewModelClass(): Class<SaveGameViewModel> {
        return SaveGameViewModel::class.java
    }

    init {
        gameId = Random.nextInt()
    }

    override fun update(savedInstanceState: Bundle?) {

        val listener  = object : OnAdapterItemClick<SaveGameViewModel.PlayerWithScore> {
            override fun onClick(playerWithScore: SaveGameViewModel.PlayerWithScore) {
                val action = SaveGameFragmentDirections.actionNavigationSaveGameToNavigationPlayerScoreBottomsheet(playerId = playerWithScore.player.id, gameId = gameId)
                findNavController().navigate(action)
            }
        }

        adapter = PlayerWithScoreAdapter()
        adapter.enableOnClickListener(listener)
        binding.rvSelectedPlayers.adapter = adapter

        binding.rvSelectedPlayers.layoutManager = GridLayoutManager(context, 2)

        setFragmentResultListener(SelectPlayersBottomSheet.REQUEST_KEY) { key, bundle ->
            val list = bundle["data"] as List<String>
            list.forEach{ t -> viewModel.getPlayerByName(t) }
        }

        setFragmentResultListener(PlayerScoreFragment.REQUEST_KEY) { key, bundle ->
            var scoreJson = bundle["data"] as String
            Timber.d(scoreJson)
            val score = Gson().fromJson(scoreJson, Score::class.java)
            viewModel.saveScore(score)
        }

        viewModel.playersLiveData.observe(
            viewLifecycleOwner
        ) { adapter.updateDataset(it) }

        viewModel.lastGameLiveData.observe(viewLifecycleOwner) { Timber.d(Gson().toJson(it)) }

        binding.btSelectPlayer.setOnClickListener {
            val directionToSelectPlayerBottomsheet =
                SaveGameFragmentDirections.directionToSelectPlayerBottomsheet()
            findNavController().navigate(directionToSelectPlayerBottomsheet) }

        binding.deleteAllButton.setOnClickListener { v -> viewModel.deleteAll() }

    }
}