package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["playerId", "gameId"])
data class Score(
    @ColumnInfo(name = "three_point_cards") val threePointsCard: Int = 0,
    @ColumnInfo(name = "five_point_cards") val fivePointsCard: Int = 0,
    @ColumnInfo(name = "eight_point_cards") val eightPointsCard: Int = 0,
    @ColumnInfo(name = "expeditions_point") val expeditionsPoints: Int = 0,
    @ColumnInfo(name = "money_point") val moneyPoints: Int = 0,
    @ColumnInfo(name = "fireworks_point") val fireworksPoints: Int = 0,
    @ColumnInfo(name = "obj_one_point") val firstObjPoints: Int = 0,
    @ColumnInfo(name = "obj_two_point") val secondObjPoints: Int = 0,
    @ColumnInfo(name = "obj_three_point") val thirdObjPoints: Int = 0,
    @ColumnInfo(name = "obj_four_point") val fourthObjPoints: Int = 0,
    @ColumnInfo(name = "obj_five_point") val fifthObjPoints: Int = 0,
    @ColumnInfo(name = "gameId") val gameId: Int = 0,
    @ColumnInfo(name = "playerId") val playerId: Int = 0
) : BaseEntity() {


}