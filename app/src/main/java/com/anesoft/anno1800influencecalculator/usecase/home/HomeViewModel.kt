package com.anesoft.anno1800influencecalculator.usecase.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anesoft.anno1800influencecalculator.base.BaseViewModel
import com.anesoft.anno1800influencecalculator.repository.local.ScoreRepository
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
)  : BaseViewModel(){

    private val _allScores : MutableLiveData<List<Score>> = MutableLiveData()
    val allScores : LiveData<List<Score>> get() = _allScores

    fun getGames(): Flow<List<Score>> {
        return scoreRepository.getAllScoresFlow()
    }

    init {
        getScores()
    }

    fun getScores() {
        viewModelScope.launch {
            val allScores = scoreRepository.getAllScores()
            _allScores.value = allScores
        }
    }

}