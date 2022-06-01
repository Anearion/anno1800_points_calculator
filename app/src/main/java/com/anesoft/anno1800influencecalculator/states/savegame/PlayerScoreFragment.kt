package com.anesoft.anno1800influencecalculator.states.savegame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.base.BaseViewBindingFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentPlayerScoreBinding
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import com.anesoft.anno1800influencecalculator.states.savegame.SaveGameUseCase.SaveGameStep.*
import com.anesoft.anno1800influencecalculator.uicomponents.SelectPlayersBottomSheet
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerScoreFragment : BaseViewBindingFragment<FragmentPlayerScoreBinding, SaveGameViewModel>() {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreation(savedInstanceState: Bundle?) {


        binding.progress.max = SaveGameUseCase.SaveGameStep.values().size
        binding.progress.progress = viewModel.currentSaveGameStep.step.ordinal
        binding.progress.isEnabled = false

        binding.pointsEditText.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.pointsEditText, InputMethodManager.SHOW_IMPLICIT)


        when (viewModel.currentSaveGameStep.step) {
            THREE_POINTS -> {
                binding.container.hint = getString(R.string.label_three_points_card_number)
                binding.progressText.text = getString(
                    R.string.label_composite_points_cards,
                    getString(R.string.label_three)
                )
            }
            FIVE_POINTS -> {
                binding.container.hint = getString(R.string.label_five_points_card_number)
                binding.progressText.text =
                    getString(R.string.label_composite_points_cards, getString(R.string.label_five))
            }
            EIGHT_POINTS -> {
                binding.container.hint = getString(R.string.label_eight_points_card_number)
                binding.progressText.text = getString(
                    R.string.label_composite_points_cards,
                    getString(R.string.label_eight)
                )
            }
            EXPEDITION -> {
                binding.container.hint = getString(R.string.label_expedition_points)
                binding.progressText.text = "Expedition Points"
            }
            GOLD -> {
                binding.container.hint = getString(R.string.label_money_points)
                binding.progressText.text = "Total Gold Coins"
            }
            OBJ_ONE -> {
                binding.container.hint = getString(R.string.label_first_obj_points)
                binding.progressText.text = "Objective One Points"
            }
            OBJ_TWO -> {
                binding.container.hint = getString(R.string.label_second_obj_points)
                binding.progressText.text = "Objective Two Points"
            }
            OBJ_THREE -> {
                binding.container.hint = getString(R.string.label_third_obj_points)
                binding.progressText.text = "Objective Three Points"
            }
            OBJ_FOUR -> {
                binding.container.hint = getString(R.string.label_fourth_obj_points)
                binding.progressText.text = "Objective Four Points"
            }
            OBJ_FIVE -> {
                binding.container.hint = getString(R.string.label_fifth_obj_points)
                binding.progressText.text = "Objective Five Points"
            }
            FIREWORK -> {
                binding.container.hint = getString(R.string.label_fireworks_points)
                binding.progressText.text = "First Player to end ?"
            }
            END -> {
                binding.progressText.text = "Done!"
                imm.hideSoftInputFromWindow(binding.pointsEditText.windowToken, 0)
                binding.pointsEditText.visibility = View.INVISIBLE
                binding.container.visibility = View.INVISIBLE
                binding.confirmButton.text = "Save"
            }
        }

        binding.pointsEditText.setText(viewModel.currentSaveGameStep.value.toString())

        binding.confirmButton.setOnClickListener {

            if (viewModel.currentSaveGameStep.step != END) {
                viewModel.nextStep(binding.pointsEditText.text.toString().toInt())
                val directions =
                    PlayerScoreFragmentDirections.actionNavigationPlayerScoreFragmentSelf(
                        args.playerId,
                        args.gameId
                    )
                findNavController().navigate(directions)
            } else {
                binding.confirmButton.isEnabled = false
                val snackbar = Snackbar.make(binding.confirmButton, "Saving", LENGTH_INDEFINITE)
                val view = snackbar.view as SnackbarLayout
                val progressBar = ProgressBar(context)
                view.addView(progressBar)
                snackbar.show()

                val saveGameStepSet = viewModel.getSaveGameStepSet()

                score = Score(
                    saveGameStepSet.find { it.step == THREE_POINTS }!!.value,
                    saveGameStepSet.find { it.step == FIVE_POINTS }!!.value,
                    saveGameStepSet.find { it.step == EIGHT_POINTS }!!.value,
                    saveGameStepSet.find { it.step == EXPEDITION }!!.value,
                    saveGameStepSet.find { it.step == GOLD }!!.value,
                    saveGameStepSet.find { it.step == FIREWORK }!!.value,
                    saveGameStepSet.find { it.step == OBJ_ONE }!!.value,
                    saveGameStepSet.find { it.step == OBJ_TWO }!!.value,
                    saveGameStepSet.find { it.step == OBJ_THREE }!!.value,
                    saveGameStepSet.find { it.step == OBJ_FOUR }!!.value,
                    saveGameStepSet.find { it.step == OBJ_FIVE }!!.value,
                    args.gameId,
                    args.playerId
                )

                viewModel.saveScore(score)

                view.removeView(progressBar)
                snackbar.setText("Saved")

                findNavController().navigate(PlayerScoreFragmentDirections.actionNavigationPlayerScoreFragmentToNavigationSaveGame())

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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (viewModel.currentSaveGameStep.step != THREE_POINTS)
                    viewModel.previousStep()
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}