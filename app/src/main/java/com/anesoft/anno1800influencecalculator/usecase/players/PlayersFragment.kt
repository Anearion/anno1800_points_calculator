package com.anesoft.anno1800influencecalculator.usecase.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayersBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PlayersFragment : BaseFragment<FragmentPlayersBinding, PlayersViewModel>() {

    private lateinit var adapter: CustomAdapter


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
            it.forEach { v ->
                Toast.makeText(context, v.name!!, Toast.LENGTH_SHORT).show()
                Timber.d("Player: %s", v.name)

            }
            populatePlayerRecyclerView(it)
        })

        viewModel.getAllPlayersObservable()

    }

    private fun populatePlayerRecyclerView(list: List<Player>?) {
        if (list != null)
            adapter = CustomAdapter(list)
        binding.rvPlayers.adapter = adapter
    }


}