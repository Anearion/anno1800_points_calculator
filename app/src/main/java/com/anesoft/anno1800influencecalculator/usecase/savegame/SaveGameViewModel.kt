package com.anesoft.anno1800influencecalculator.usecase.savegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.repository.local.PlayerRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveGameViewModel @Inject constructor(private val playerRepository: PlayerRepository) : BaseViewModel() {

    private var _playersLiveData : MutableLiveData<List<Player>> = MutableLiveData()
    val playersLiveData : LiveData<List<Player>> get() = _playersLiveData

    fun getPlayerByName(name :String){
        viewModelScope.launch {
            val playerByName = playerRepository.getPlayerByName(name)
            var pList = mutableListOf<Player>()
            if(_playersLiveData.value != null){
                pList = _playersLiveData.value!!.toMutableList()
            }

            pList.add(playerByName)
            _playersLiveData.setValue(pList)
        }

    }

}
