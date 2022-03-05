package com.anesoft.anno1800influencecalculator.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anesoft.anno1800influencecalculator.repository.local.dao.PlayerDao
import com.anesoft.anno1800influencecalculator.repository.local.dao.ScoreDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score

@Database(entities = [Player::class, Score::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao() : PlayerDao

    abstract fun scoreDao() : ScoreDao

}