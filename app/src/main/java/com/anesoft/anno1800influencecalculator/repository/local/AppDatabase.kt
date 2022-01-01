package com.anesoft.anno1800influencecalculator.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anesoft.anno1800influencecalculator.repository.local.dao.PlayerDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player

@Database(entities = [Player::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao() : PlayerDao

}