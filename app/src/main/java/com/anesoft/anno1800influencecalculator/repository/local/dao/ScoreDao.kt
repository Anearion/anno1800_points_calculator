package com.anesoft.anno1800influencecalculator.repository.local.dao

import androidx.room.*
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("SELECT * FROM Score")
    suspend fun getAll() : List<Score>

    @Query("SELECT * FROM Score")
    fun getAllObservable() : Flow<List<Score>>

    @Query("SELECT * FROM Score WHERE Score.gameId = :gameId")
    fun getScoreHistoryForGame(gameId: Int) : Flow<List<Score>>

    @Insert
    suspend fun insert(score: Score)

    @Delete
    fun delete(score: Score)

    @Query("DELETE FROM Score")
    suspend fun deleteAll()

}