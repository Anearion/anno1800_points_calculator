package com.anesoft.anno1800influencecalculator.states.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.model.Game
import com.anesoft.anno1800influencecalculator.repository.local.PlayerRepository
import com.anesoft.anno1800influencecalculator.repository.local.ScoreRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val scoreRepository: ScoreRepository
)  : BaseViewModel(){

    private val _allScores : MutableLiveData<List<Game>> = MutableLiveData()
    val allScores : LiveData<List<Game>> get() = _allScores

    fun getGames(): Flow<List<Score>> {
        return scoreRepository.getAllScoresFlow()
    }

    override fun onResume() {
        super.onResume()
        getScores()
    }

    private fun getScores() {
        viewModelScope.launch {
            val allScores = scoreRepository.getAllScores()
            val games = allScores.groupBy { it.gameId }
            val gameList = mutableListOf<Game>()
            games.forEach { it ->
                val playerId = it.value.sortedByDescending { it.getPoints() }[0].playerId
                val player = playerRepository.find(playerId)
                val game = Game(it.value, player.name!!, it.value.size)
                gameList.add(game)
            }

            _allScores.value = gameList
        }
    }

}