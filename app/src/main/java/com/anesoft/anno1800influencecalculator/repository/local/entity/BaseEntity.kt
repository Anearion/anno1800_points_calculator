package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class BaseEntity(
    @ColumnInfo(name = "created")
    var created: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated")
    var updated: Long = System.currentTimeMillis()
)