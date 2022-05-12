package com.anesoft.anno1800influencecalculator.states.savegame

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayerScoreBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.states.savegame.SaveGameUseCase.SaveGameStep.*
import com.anesoft.anno1800influencecalculator.uicomponents.SelectPlayersBottomSheet
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerScoreFragment : BaseFragment<FragmentPlayerScoreBinding, SaveGameViewModel>() {

    val args: PlayerScoreFragmentArgs by navArgs()
    lateinit var score: Score
    override var useSharedViewModel = true

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

        binding.progress.max = SaveGameUseCase.SaveGameStep.values().size
        binding.progress.progress = viewModel.currentSaveGameStep.step.ordinal

        binding.progressText.text = viewModel.currentSaveGameStep.step.name

        when (viewModel.currentSaveGameStep.step) {
            THREE_POINTS -> binding.container.hint = getString(R.string.label_three_points_card_number)
            FIVE_POINTS -> binding.container.hint = getString(R.string.label_five_points_card_number)
            EIGHT_POINTS -> binding.container.hint = getString(R.string.label_eight_points_card_number)
            EXPEDITION -> binding.container.hint = getString(R.string.label_expedition_points)
            GOLD -> binding.container.hint = getString(R.string.label_money_points)
            OBJ_ONE -> binding.container.hint = getString(R.string.label_first_obj_points)
            OBJ_TWO -> binding.container.hint = getString(R.string.label_second_obj_points)
            OBJ_THREE -> binding.container.hint = getString(R.string.label_third_obj_points)
            OBJ_FOUR -> binding.container.hint = getString(R.string.label_fourth_obj_points)
            OBJ_FIVE -> binding.container.hint = getString(R.string.label_fifth_obj_points)
            FIREWORK -> binding.container.hint = getString(R.string.label_fireworks_points)
            END -> binding.container.hint = "FINITO"
        }

        binding.pointsEditText.setText(viewModel.currentSaveGameStep.value.toString())

        binding.confirmButton.setOnClickListener {
            viewModel.nextStep(binding.pointsEditText.text.toString().toInt())
            if (viewModel.currentSaveGameStep.step != END) {
                val directions =
                    PlayerScoreFragmentDirections.actionNavigationPlayerScoreFragmentSelf(
                        args.playerId,
                        args.gameId
                    )
                findNavController().navigate(directions)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.currentSaveGameStep.step != THREE_POINTS)
                        viewModel.previousStep()
                    findNavController().navigateUp()
                }

            })

//        viewModel.getScoreByPlayerAndGame(args.playerId, args.gameId)
//
//        viewModel.scoreByPlayerAndGameLiveData.observe(viewLifecycleOwner) { populateScore(it) }
//
//        binding.confirmButton.setOnClickListener { v ->
//
//            val points3 = binding.threePointsET.text.toString().toInt()
//            val points5 = binding.fivePointsET.text.toString().toInt()
//            val points8 = binding.eightPointsET.text.toString().toInt()
//
//
//
//            score = Score(
//                points3,
//                points5,
//                points8,
//                binding.expeditionePointsET.text.toString().toInt(),
//                binding.moneyPointsET.text.toString().toInt(),
//                binding.fireworksPointsET.text.toString().toInt(),
//                0,
//                0,
//                0,
//                0,
//                0,
//                args.gameId,
//                args.playerId
//            )
//
//            val result = Gson().toJson(score)
//
//            setFragmentResult(
//                REQUEST_KEY,
//                bundleOf("data" to result)
//            )
//            findNavController().navigateUp()
//        }
    }

    private fun populateScore(it: Score?) {
        if (it == null) return

        score = it

        /*    binding.threePointsET.setText(it.threePointsCard.toString())
            binding.fivePointsET.setText(it.fivePointsCard.toString())
            binding.eightPointsET.setText(it.eightPointsCard.toString())
            binding.expeditionePointsET.setText(it.expeditionsPoints.toString())
            binding.moneyPointsET.setText(it.moneyPoints.toString())
            binding.fireworksPointsET.setText(it.fireworksPoints.toString())*/

    }
}