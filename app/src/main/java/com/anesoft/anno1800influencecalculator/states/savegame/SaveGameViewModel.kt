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
import kotlin.random.Random

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
        var score: Int = 0
    )

    var gameId: Int = 0

    init {
        gameId = Random.nextInt()
    }

    var currentSaveGameStep  = SaveGameUseCase(SaveGameUseCase.SaveGameStep.THREE_POINTS, 0)
    private val saveGameStepList = mutableSetOf<SaveGameUseCase>()

    private var _playersWithScoreLiveData: MutableLiveData<List<PlayerWithScore>> = MutableLiveData()
    val playersWithScoreLiveData: LiveData<List<PlayerWithScore>> get() = _playersWithScoreLiveData

    private var _playersLiveData: MutableLiveData<List<Player>> = MutableLiveData()
    val playersLiveData: LiveData<List<Player>> get() = _playersLiveData

    private var _scoreByPlayerAndGameLiveData : MutableLiveData<Score> = MutableLiveData()
    val scoreByPlayerAndGameLiveData : LiveData<Score> get() = _scoreByPlayerAndGameLiveData

    fun updatePlayers() {
        viewModelScope.launch {
            val scoreByGame = scoreRepository.getScoreListByGameId(gameId)
            var pList = mutableListOf<PlayerWithScore>()


            scoreByGame.let { scores->
                scores.forEach { score->
                    val playerList = _playersLiveData.value
                    val player = playerList?.find { it.id == score.playerId }
                    player.let { pList.add(PlayerWithScore(it!!, score.getPoints())) }
                }
                _playersWithScoreLiveData.value = pList
            }
        }
    }

    fun getSaveGameStepSet() : Set<SaveGameUseCase> {
        return saveGameStepList
    }

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

    fun getPlayerByName(names: List<String>) {
        viewModelScope.launch {
            var pList = mutableListOf<Player>()
            names.forEach {
                val playerByName = playerRepository.getPlayerByName(it)
                pList.add(playerByName)
            }
            _playersLiveData.setValue(pList)
        }
    }

    fun saveScore(score: Score){
        viewModelScope.launch {
            scoreRepository.saveScore(score)
            updatePlayers()
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
