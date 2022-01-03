package com.anesoft.anno1800influencecalculator.uicomponents

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anesoft.anno1800influencecalculator.databinding.FragmentSelectPlayersBottomSheetListDialogBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.usecase.players.PlayersViewModel
import dagger.hilt.android.AndroidEntryPoint


const val ARG_ITEM_COUNT = "item_count"

@AndroidEntryPoint
class SelectPlayersBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectPlayersBottomSheetListDialogBinding? = null

    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            FragmentSelectPlayersBottomSheetListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllPlayers()
        viewModel.playersLiveData.observe(
            viewLifecycleOwner,
            { t ->
                run {
                    t.forEach { player -> addCheckBox(player) }
                }
                binding.btDone.isEnabled = true
            }
        )

        binding.btDone.setOnClickListener { v ->

            setFragmentResult(REQUEST_KEY, bundleOf("data" to viewModel.selectedPlayers))
            findNavController().navigateUp()
        }

    }

    private fun addCheckBox(player: Player) {
        val checkBox = CheckBox(context)
        checkBox.text = player.name
        checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                viewModel.addSelectedPlayer(checkBox.text.toString())
            }
        }
        binding.container.addView(checkBox)
        binding.container.invalidate()
    }


    companion object {

        val REQUEST_KEY = "SELECTED_PLAYERS_KEY"

        fun newInstance(itemCount: Int): SelectPlayersBottomSheet =
            SelectPlayersBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}