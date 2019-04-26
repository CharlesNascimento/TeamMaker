package com.kansus.teammaker.android.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "GamePlayer",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
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
        Index("gameId"),
        Index("playerId")
    ]
)
data class GamePlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gameId: Int,
    val playerId: Int
) {
    constructor(gameId: Int, playerId: Int) : this(0, gameId, playerId)
}