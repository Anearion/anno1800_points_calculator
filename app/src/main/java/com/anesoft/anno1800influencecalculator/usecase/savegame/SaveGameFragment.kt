package com.anesoft.anno1800influencecalculator.usecase.savegame

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentSaveGameBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.uicomponents.PlayerScoreBottomSheet
import com.anesoft.anno1800influencecalculator.uicomponents.SelectPlayersBottomSheet
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.OnAdapterItemClick
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.PlayerNameAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SaveGameFragment : BaseFragment<FragmentSaveGameBinding, SaveGameViewModel>() {

    private lateinit var adapter: PlayerNameAdapter

    val listener  = object : OnAdapterItemClick {
        override fun onClick(player: Player) {
            findNavController().navigate(R.id.navigation_player_score_bottomsheet)
        }
    }

    override fun getViewModelClass(): Class<SaveGameViewModel> {
        return SaveGameViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {

        adapter = PlayerNameAdapter()
        adapter.enableOnClickListener(listener)
        binding.rvSelectedPlayers.adapter = adapter

        binding.rvSelectedPlayers.layoutManager = LinearLayoutManager(context)

        setFragmentResultListener(SelectPlayersBottomSheet.REQUEST_KEY) { key, bundle ->
            val list = bundle["data"] as List<String>
            list.forEach{ t -> viewModel.getPlayerByName(t) }
        }

        setFragmentResultListener(PlayerScoreBottomSheet.REQUEST_KEY) { key, bundle ->
            var score = bundle["data"] as String
            Timber.d(score)
        }

        viewModel.playersLiveData.observe(
            viewLifecycleOwner,
            { adapter.updateDataset(it) }
        )

        binding.btSelectPlayer.setOnClickListener { v ->
            findNavController().navigate(R.id.navigation_select_player_bottomsheet) }
    }
}