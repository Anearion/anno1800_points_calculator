package com.anesoft.anno1800influencecalculator.repository.local

import com.anesoft.anno1800influencecalculator.repository.local.dao.ScoreDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScoreRepository @Inject constructor(private val scoreDao: ScoreDao){

    suspend fun getAllPlayers(): List<Score> {
        return scoreDao.getAll()
    }

    fun getAllPlayersFlow() : Flow<List<Score>> {
        return scoreDao.getAllObservable()
    }

    suspend fun savePlayer(p : Score){
        scoreDao.insert(p)
    }

    suspend fun deleteAll() {
        scoreDao.deleteAll()
    }

}