package com.anesoft.anno1800influencecalculator.usecase.players

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayersBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.PlayerNameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayersFragment : BaseFragment<FragmentPlayersBinding, PlayersViewModel>() {

    private var adapter = PlayerNameAdapter()


    override fun getViewModelClass(): Class<PlayersViewModel> {
        return PlayersViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {

        binding.rvPlayers.layoutManager = LinearLayoutManager(context)


        binding.clearDB.setOnClickListener {
            viewModel.deleteAll()
        }

        binding.fabCreatePlayer.setOnClickListener {
            findNavController().navigate(R.id.navigation_create_player)
        }

        viewModel.playersLiveData.observe(viewLifecycleOwner, {
            populatePlayerRecyclerView(it)
        })

        viewModel.getAllPlayersObservable()

    }

    private fun populatePlayerRecyclerView(list: List<Player>?) {
        if (list != null)
            adapter.updateDataset(list)
        binding.rvPlayers.adapter = adapter
    }


}