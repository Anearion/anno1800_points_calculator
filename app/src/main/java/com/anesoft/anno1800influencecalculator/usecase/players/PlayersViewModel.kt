package com.anesoft.anno1800influencecalculator.usecase.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.repository.local.PlayerRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val playerRepository: PlayerRepository) : BaseViewModel() {


    private var _playersLiveData : MutableLiveData<List<Player>> = MutableLiveData()
    val playersLiveData : LiveData<List<Player>> get() = _playersLiveData

    var selectedPlayers = mutableListOf<String>()

    fun savePlayer(p: Player) {
        viewModelScope.launch {
            playerRepository.savePlayer(p)
        }
    }

    fun getAllPlayers(){
        viewModelScope.launch {
            val allPlayers = playerRepository.getAllPlayers()
            _playersLiveData.setValue(allPlayers)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            playerRepository.deleteAll()
        }
    }

    fun getAllPlayersObservable(){
        viewModelScope.launch {
            playerRepository.getAllPlayersFlow().collect {
                _playersLiveData.setValue(it)
            }
        }

    }

    fun addSelectedPlayer(text: String) {
        selectedPlayers.add(text)
    }


}