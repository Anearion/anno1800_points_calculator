package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class BaseEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "created")
    var created: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated")
    var updated: Long = System.currentTimeMillis()
) {
    constructor(created: Long, updated: Long) : this(0, created, updated)
}