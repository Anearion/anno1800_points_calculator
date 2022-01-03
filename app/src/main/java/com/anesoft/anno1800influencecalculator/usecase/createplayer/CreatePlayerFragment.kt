package com.anesoft.anno1800influencecalculator.usecase.createplayer

import android.os.Bundle
import android.widget.RadioGroup
import com.anesoft.anno1800influencecalculator.base.BaseFragment
import com.anesoft.anno1800influencecalculator.databinding.FragmentCreatePlayerBinding
import android.widget.RadioButton
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.usecase.players.PlayersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlayerFragment : BaseFragment<FragmentCreatePlayerBinding, PlayersViewModel>() {

    private var gender = String()

    override fun getViewModelClass(): Class<PlayersViewModel> {
        return PlayersViewModel::class.java
    }

    override fun update(savedInstanceState: Bundle?) {
        gender = requireContext().getString(R.string.male)
        binding.rbMale.isChecked = true

        binding.rgSex.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { rg, checkedId ->
            for (i in 0 until rg.childCount) {
                val btn = rg.getChildAt(i) as RadioButton
                if (btn.id == checkedId) {
                    gender = btn.text.toString()
                    return@OnCheckedChangeListener
                }
            }
        })


        binding.btSave.setOnClickListener {
            run {
                val name = binding.etName.text.toString()
                viewModel.savePlayer(Player(name, gender))
            }
        }

    }


}