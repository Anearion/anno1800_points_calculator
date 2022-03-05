package com.anesoft.anno1800influencecalculator.usecase.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentHomeBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.GameRecapAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var adapter = GameRecapAdapter()

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {

        binding.gameList.layoutManager = LinearLayoutManager(context)

        binding.fabCreatePlayer.setOnClickListener {
            findNavController().navigate(R.id.navigation_save_game)
        }


        viewModel.allScores.observe(viewLifecycleOwner, {
            val games = it.groupBy { it.gameId }
            Timber.d(Gson().toJson(games))
            populateList(it)
        })

    }

    private fun populateList(it: List<Score>?) {

        if(it != null) adapter.updateDataset(it)
        binding.gameList.adapter = adapter
    }


}