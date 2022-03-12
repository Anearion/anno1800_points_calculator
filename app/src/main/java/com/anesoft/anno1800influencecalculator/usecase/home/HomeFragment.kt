package com.anesoft.anno1800influencecalculator.usecase.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentHomeBinding
import com.anesoft.anno1800influencecalculator.model.Game
import com.anesoft.anno1800influencecalculator.uicomponents.adapters.GameRecapAdapter
import dagger.hilt.android.AndroidEntryPoint

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