package com.anesoft.anno1800influencecalculator.repository.local.dao

import androidx.room.*
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("SELECT * FROM Score")
    suspend fun getAll() : List<Score>

    @Query("SELECT * FROM Score GROUP BY gameId")
    fun getAllObservable() : Flow<List<Score>>

    @Query("SELECT * FROM Score WHERE Score.gameId = :gameId")
    fun getScoreHistoryForGame(gameId: Int) : Flow<List<Score>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: Score)

    @Delete
    fun delete(score: Score)

    @Query("DELETE FROM Score")
    suspend fun deleteAll()

    @Query("SELECT * FROM Score ORDER BY Score.gameId DESC")
    suspend fun getLastGame() : Score

    @Query("SELECT * FROM Score WHERE Score.playerId = :playerId AND Score.gameId = :gameId")
    suspend fun getScoreByPlayerAndGame(playerId: Int, gameId: Int) : Score

}