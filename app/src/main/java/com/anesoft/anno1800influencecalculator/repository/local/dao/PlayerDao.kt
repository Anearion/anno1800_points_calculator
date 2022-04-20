package com.anesoft.anno1800influencecalculator.repository.local.dao

import androidx.room.*
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import kotlinx.coroutines.flow.Flow
import java.sql.RowId

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Player")
    suspend fun getAll() : List<Player>

    @Query("SELECT * FROM Player WHERE Player.first_name = :name")
    suspend fun getByName(name: String) : Player

    @Query("SELECT * FROM Player WHERE Player.id = :id")
    suspend fun find(id: Int) : Player

    @Query("SELECT * FROM Player")
    fun getAllObservable() : Flow<List<Player>>

    @Query("SELECT * FROM Player JOIN Score ON Score.playerID =:playerId")
    suspend fun getScoreHistoryForPlayer(playerId: Int) : Map<Player, List<Score>>

    @Insert
    suspend fun insertAll(vararg players:Player)

    @Insert
    suspend fun insert(players:Player) : Long

    @Delete
    fun delete(player: Player)

    @Query("DELETE FROM Player")
    suspend fun deleteAll()

}