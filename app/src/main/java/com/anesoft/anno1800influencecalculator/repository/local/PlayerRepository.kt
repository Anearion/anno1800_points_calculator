package com.anesoft.anno1800influencecalculator.repository.local

import com.anesoft.anno1800influencecalculator.repository.local.dao.PlayerDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepository @Inject constructor(private val playerDao: PlayerDao){

    suspend fun getAllPlayers(): List<Player> {
        return playerDao.getAll()
    }

    suspend fun getPlayerByName(name: String) : Player {
        return playerDao.getByName(name)
    }

    suspend fun find(id: Int) : Player {
        return playerDao.find(id)
    }

    fun getAllPlayersFlow() : Flow<List<Player>> {
        return playerDao.getAllObservable()
    }

    suspend fun savePlayer(p : Player){
        playerDao.insertAll(p)
    }

    suspend fun deleteAll() {
        playerDao.deleteAll()
    }

}