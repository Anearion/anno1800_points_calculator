package com.anesoft.anno1800influencecalculator.states.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.repository.local.PlayerRepository
import com.anesoft.anno1800influencecalculator.repository.local.ScoreRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val scoreRepository: ScoreRepository
) : BaseViewModel() {

    private val _allScores : MutableLiveData<List<Score>> = MutableLiveData()
    val allScores : LiveData<List<Score>> get() = _allScores

  fun getGameScores(gameId : Int){
      viewModelScope.launch {
          val scores = scoreRepository.getScoreListByGameId(gameId)
          _allScores.value = scores
      }
  }
}