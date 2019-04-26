package com.kansus.teammaker.android.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FixtureTeam")
data class FixtureTeamEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val gameId: Int,
    val playerId: Int
)