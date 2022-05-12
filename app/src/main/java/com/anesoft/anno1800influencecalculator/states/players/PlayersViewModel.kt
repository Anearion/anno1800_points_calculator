package com.anesoft.anno1800influencecalculator.states.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.repository.local.PlayerRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val playerRepository: PlayerRepository) :
    BaseViewModel() {

    data class ViewState(val error: Exception?, val playerList: List<Player>?, val playerId : Long?)


    private var _playersLiveData: MutableLiveData<List<Player>> = MutableLiveData()
    val playersLiveData: LiveData<List<Player>> get() = _playersLiveData

    private var _savePlayerLiveData : MutableLiveData<ViewState> = MutableLiveData()
    val savePlayerLiveData : LiveData<ViewState> get() = _savePlayerLiveData

    var selectedPlayers = mutableListOf<String>()

    fun savePlayer(p: Player) {
        viewModelScope.launch {
            try {
                val playerId = playerRepository.savePlayer(p)
                _savePlayerLiveData.setValue(ViewState(null, null, playerId))
            } catch (ex : Exception){
                Timber.e(ex)
                _savePlayerLiveData.setValue(ViewState(ex, null, null))
            }
        }
    }

    fun getAllPlayers() {
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

    fun getAllPlayersObservable() {
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