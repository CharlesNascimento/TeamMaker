package com.kansus.teammaker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TeamPlayer")
data class TeamPlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val teamId: Int,
    val playerId: Int
)