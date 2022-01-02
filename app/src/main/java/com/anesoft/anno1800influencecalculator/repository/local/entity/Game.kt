package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["playerId", "scoreId"])
data class Game(
    val playerId: Int,
    val scoreId: Int
)  {
}