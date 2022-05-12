package com.anesoft.anno1800influencecalculator.repository.local

import com.anesoft.anno1800influencecalculator.repository.local.dao.ScoreDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScoreRepository @Inject constructor(private val scoreDao: ScoreDao){

    suspend fun getAllScores(): List<Score> {
        return scoreDao.getAll()
    }

    fun getAllScoresFlow() : Flow<List<Score>> {
        return scoreDao.getAllObservable()
    }

    suspend fun saveScore(p : Score){
        scoreDao.insert(p)
    }

    suspend fun deleteAll() {
        scoreDao.deleteAll()
    }

    suspend fun getLastGame() : Score{
        return scoreDao.getLastGame()
    }

    suspend fun getScoreByPlayerAndGame(playerId: Int, gameId: Int): Score {
        return scoreDao.getScoreByPlayerAndGame(playerId, gameId)
    }

    suspend fun getScoreListByGameId(gameId: Int): List<Score> {
        return scoreDao.getScoreListByGameId(gameId)
    }

}