package com.anesoft.anno1800influencecalculator.uicomponents

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayerScoreBinding
import com.anesoft.anno1800influencecalculator.databinding.FragmentSelectPlayersBottomSheetListDialogBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.usecase.players.PlayersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class PlayerScoreBottomSheet: BottomSheetDialogFragment() {

    private var _binding: FragmentPlayerScoreBinding? = null

    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            FragmentPlayerScoreBinding.inflate(inflater, container, false)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }
    

    companion object {

        val REQUEST_KEY = "SELECTED_PLAYERS_KEY"

        fun newInstance(): SelectPlayersBottomSheet =
            SelectPlayersBottomSheet().apply {
                arguments = Bundle().apply {

                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}