package com.anesoft.anno1800influencecalculator.uicomponents

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayerScoreBinding
import com.anesoft.anno1800influencecalculator.databinding.FragmentSelectPlayersBottomSheetListDialogBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.usecase.players.PlayersViewModel
import com.anesoft.anno1800influencecalculator.usecase.savegame.SaveGameViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerScoreBottomSheet : BaseFragment<FragmentPlayerScoreBinding, SaveGameViewModel>() {

    val args : PlayerScoreBottomSheetArgs by navArgs()
    lateinit var score : Score

    companion object {

        val REQUEST_KEY = "PLAYER_SCORE_KEY"

        fun newInstance(): SelectPlayersBottomSheet =
            SelectPlayersBottomSheet().apply {
                arguments = Bundle().apply {

                }
            }

    }

    override fun getViewModelClass(): Class<SaveGameViewModel> {
        return SaveGameViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {

        viewModel.getScoreByPlayerAndGame(args.playerId, args.gameId)

        viewModel.scoreByPlayerAndGameLiveData.observe(viewLifecycleOwner, {populateScore(it)})

        binding.confirmButton.setOnClickListener { v ->

            val points3 = binding.threePointsET.text.toString().toInt()
            val points5 = binding.fivePointsET.text.toString().toInt()
            val points8 = binding.eightPointsET.text.toString().toInt()



            score = Score(
                points3,
                points5,
                points8,
                binding.expeditionePointsET.text.toString().toInt(),
                binding.moneyPointsET.text.toString().toInt(),
                binding.fireworksPointsET.text.toString().toInt(),
                0,
                0,
                0,
                0,
                0,
                args.gameId,
                args.playerId
            )

            val result = Gson().toJson(score)

            setFragmentResult(
                REQUEST_KEY,
                bundleOf("data" to result)
            )
            findNavController().navigateUp()
        }
    }

    private fun populateScore(it: Score?) {
        if (it == null) return

        score = it

        binding.threePointsET.setText(it.threePointsCard.toString())
        binding.fivePointsET.setText(it.fivePointsCard.toString())
        binding.eightPointsET.setText(it.eightPointsCard.toString())
        binding.expeditionePointsET.setText(it.expeditionsPoints.toString())
        binding.moneyPointsET.setText(it.moneyPoints.toString())
        binding.fireworksPointsET.setText(it.fireworksPoints.toString())

    }
}