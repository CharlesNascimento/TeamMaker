package com.kansus.teammaker.android.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TeamPlayer",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("teamId"),
        Index("playerId")
    ]
)
data class TeamPlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val teamId: Int,
    val playerId: Int
)