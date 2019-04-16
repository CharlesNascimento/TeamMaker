package com.kansus.teammaker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "Fixture",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = CASCADE
        )
    ],indices = [ Index("gameId") ]
)
data class FixtureEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: Date,
    var gameId: Int
)