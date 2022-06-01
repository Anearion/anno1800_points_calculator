package com.anesoft.anno1800influencecalculator.states.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anesoft.anno1800influencecalculator.base.BaseViewBindingFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentHomeBinding
import com.anesoft.anno1800influencecalculator.model.Game
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.GameRecapAdapter
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.OnAdapterItemClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding, HomeViewModel>() {

    private var adapter = GameRecapAdapter()

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onViewCreation(savedInstanceState: Bundle?) {

        binding.gameList.layoutManager = LinearLayoutManager(context)

        val listener  = object : OnAdapterItemClick<Game> {
            override fun onClick(game: Game) {
                val action = HomeFragmentDirections.actionHomeToNavigationFragmentGame(gameId = game.scoreList[0].gameId)
                findNavController().navigate(action)
            }
        }
        adapter.enableOnClickListener(listener)

        binding.fabCreatePlayer.setOnClickListener {
            val actionHomeToNavigationSaveGame =
                HomeFragmentDirections.actionHomeToNavigationSaveGame()
            findNavController().navigate(actionHomeToNavigationSaveGame)
        }


        viewModel.allScores.observe(viewLifecycleOwner) { it ->
            if(it.isNotEmpty()) binding.tvEmptyMessage.visibility = View.GONE
            else binding.tvEmptyMessage.visibility = View.VISIBLE
            populateList(it)
        }

    }

    private fun populateList(it: List<Game>) {
        adapter.updateDataset(it)
        binding.gameList.adapter = adapter
    }


}