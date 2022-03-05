package com.anesoft.anno1800influencecalculator.usecase.savegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.repository.local.PlayerRepository
import com.anesoft.anno1800influencecalculator.repository.local.ScoreRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveGameViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val scoreRepository: ScoreRepository
) : BaseViewModel() {

    data class SaveGameViewState(
        val playerList: ArrayList<Player> = arrayListOf(),
        val score: Score
    )

    private val _state = MutableLiveData<SaveGameViewState>()
    val state : LiveData<SaveGameViewState> get() = _state

    init {
        _state.value = SaveGameViewState(arrayListOf(), Score())
    }

    private var _playersLiveData: MutableLiveData<List<Player>> = MutableLiveData()
    val playersLiveData: LiveData<List<Player>> get() = _playersLiveData

    private var _lastGameLiveData: MutableLiveData<Score> = MutableLiveData()
    val lastGameLiveData: LiveData<Score> get() = _lastGameLiveData

    private var _scoreByPlayerAndGameLiveData : MutableLiveData<Score> = MutableLiveData()
    val scoreByPlayerAndGameLiveData : LiveData<Score> get() = _scoreByPlayerAndGameLiveData

    fun getPlayerByName(name: String) {
        viewModelScope.launch {
            val playerByName = playerRepository.getPlayerByName(name)
            var pList = mutableListOf<Player>()
            if (_playersLiveData.value != null) {
                pList = _playersLiveData.value!!.toMutableList()
            }

            pList.add(playerByName)
            _playersLiveData.setValue(pList)
        }
    }

    fun getLastGame() {
        viewModelScope.launch {
            val lastGame = scoreRepository.getLastGame()
            _lastGameLiveData.value = lastGame
        }
    }

    fun saveScore(score: Score){
        viewModelScope.launch {
            scoreRepository.saveScore(score)
        }
    }

    fun getScoreByPlayerAndGame(playerId: Int, gameId: Int) {
        viewModelScope.launch {
            val score = scoreRepository.getScoreByPlayerAndGame(playerId, gameId)
            _scoreByPlayerAndGameLiveData.value = score
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            scoreRepository.deleteAll()
        }
    }

}
