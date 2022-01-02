package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @ColumnInfo(name = "three_point_cards") val threePointsCard: Int,
    @ColumnInfo(name = "five_point_cards") val fivePointsCard: Int,
    @ColumnInfo(name = "eight_point_cards") val eightPointsCard: Int,
    @ColumnInfo(name = "expeditions_point") val expeditionsPoints: Int,
    @ColumnInfo(name = "money_point") val moneyPoints: Int,
    @ColumnInfo(name = "fireworks_point") val fireworksPoints: Int,
    @ColumnInfo(name = "obj_one_point") val firstObjPoints: Int,
    @ColumnInfo(name = "obj_two_point") val secondObjPoints: Int,
    @ColumnInfo(name = "obj_three_point") val thirdObjPoints: Int,
    @ColumnInfo(name = "obj_four_point") val fourthObjPoints: Int,
    @ColumnInfo(name = "obj_five_point") val fifthObjPoints: Int,
    @ColumnInfo(name = "gameId") val gameId: Int,
    @ColumnInfo(name = "playerId") val playerId: Int
) : BaseEntity() {

}