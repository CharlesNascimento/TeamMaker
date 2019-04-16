package com.kansus.teammaker.data.entity.join

import androidx.room.Embedded
import androidx.room.Relation
import com.kansus.teammaker.data.entity.PlayerEntity
import com.kansus.teammaker.data.entity.TeamEntity
import com.kansus.teammaker.data.entity.TeamPlayerEntity

data class TeamWithPlayers(
    @Embedded
    val team: TeamEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "teamId",
        entity = TeamPlayerEntity::class
    ) val players: List<PlayerEntity>
)