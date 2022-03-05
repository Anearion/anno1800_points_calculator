package com.anesoft.anno1800influencecalculator.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class BaseEntityPrimaryKey(
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) : BaseEntity()