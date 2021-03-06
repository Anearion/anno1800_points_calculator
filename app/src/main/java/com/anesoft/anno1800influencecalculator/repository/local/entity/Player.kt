package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "player", indices = [Index(value = ["first_name"], unique = true)])
data class Player(
    @ColumnInfo(name = "first_name") val name: String?,
    @ColumnInfo(name = "gender") val gender: String?
) : BaseEntityPrimaryKey() {

}