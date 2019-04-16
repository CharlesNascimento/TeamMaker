package com.kansus.teammaker.data.entity

import androidx.room.*

@Entity(
    tableName = "Player",
    indices = [Index("name")]
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String
)
