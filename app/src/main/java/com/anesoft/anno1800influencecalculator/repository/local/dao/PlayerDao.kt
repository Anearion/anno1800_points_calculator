package com.anesoft.anno1800influencecalculator.repository.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Player")
    suspend fun getAll() : List<Player>

    @Query("SELECT * FROM Player WHERE Player.first_name = :name")
    suspend fun getByName(name: String) : Player

    @Query("SELECT * FROM Player")
    fun getAllObservable() : Flow<List<Player>>

    @Query("SELECT * FROM Player JOIN Score ON Score.playerID =:playerId")
    suspend fun getScoreHistoryForPlayer(playerId: Int) : Map<Player, List<Score>>

    @Insert
    suspend fun insertAll(vararg players:Player)

    @Delete
    fun delete(player: Player)

    @Query("DELETE FROM Player")
    suspend fun deleteAll()

}