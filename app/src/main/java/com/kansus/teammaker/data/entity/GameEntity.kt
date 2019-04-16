package com.kansus.teammaker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Game")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var description: String
)
