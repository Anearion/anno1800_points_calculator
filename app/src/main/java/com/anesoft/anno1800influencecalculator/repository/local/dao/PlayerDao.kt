package com.anesoft.anno1800influencecalculator.repository.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Player")
    suspend fun getAll() : List<Player>

    @Query("SELECT * FROM Player")
    fun getAllObservable() : Flow<List<Player>>

    @Insert
    suspend fun insertAll(vararg players:Player)

    @Delete
    fun delete(player: Player)

    @Query("DELETE FROM Player")
    suspend fun deleteAll()

}