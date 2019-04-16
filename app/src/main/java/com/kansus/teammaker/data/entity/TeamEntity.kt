package com.kansus.teammaker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "Team",
    foreignKeys = [
        ForeignKey(
            entity = FixtureEntity::class,
            parentColumns = ["id"],
            childColumns = ["fixtureId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("fixtureId")]
)
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: Date,
    var fixtureId: Int
)