package com.anesoft.anno1800influencecalculator.states.savegame

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

    data class PlayerWithScore(
        val player: Player,
        val score: Int = 0
    )

    var currentSaveGameStep  = SaveGameUseCase(SaveGameUseCase.SaveGameStep.THREE_POINTS, 0)
    private val saveGameStepList = mutableSetOf<SaveGameUseCase>()

    private val _state = MutableLiveData<SaveGameViewState>()
    val state : LiveData<SaveGameViewState> get() = _state

    init {
        _state.value = SaveGameViewState(arrayListOf(), Score())
    }

    private var _playersLiveData: MutableLiveData<List<PlayerWithScore>> = MutableLiveData()
    val playersLiveData: LiveData<List<PlayerWithScore>> get() = _playersLiveData

    private var _lastGameLiveData: MutableLiveData<Score> = MutableLiveData()
    val lastGameLiveData: LiveData<Score> get() = _lastGameLiveData

    private var _scoreByPlayerAndGameLiveData : MutableLiveData<Score> = MutableLiveData()
    val scoreByPlayerAndGameLiveData : LiveData<Score> get() = _scoreByPlayerAndGameLiveData

    fun nextStep(value: Int){
        if(saveGameStepList.contains(currentSaveGameStep)){
            saveGameStepList.elementAt(saveGameStepList.indexOf(currentSaveGameStep)).value = value
        }else{
            currentSaveGameStep.value = value
            saveGameStepList.add(currentSaveGameStep)
        }
        currentSaveGameStep = currentSaveGameStep.nextStep()
        if(saveGameStepList.contains(currentSaveGameStep)){
            currentSaveGameStep = saveGameStepList.elementAt(saveGameStepList.indexOf(currentSaveGameStep))
        }
    }

    fun previousStep(){
        currentSaveGameStep = saveGameStepList.elementAt(saveGameStepList.indexOf(currentSaveGameStep.previousStep()))
    }

    fun getPlayerByName(name: String) {
        viewModelScope.launch {
            val playerByName = playerRepository.getPlayerByName(name)
            var pList = mutableListOf<PlayerWithScore>()
            if (_playersLiveData.value != null) {
                pList = _playersLiveData.value!!.toMutableList()
            }

            pList.add(PlayerWithScore(playerByName))
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
