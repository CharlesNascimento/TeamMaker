package com.kansus.teammaker.android.data.entity.join

import androidx.room.Embedded
import androidx.room.Relation
import com.kansus.teammaker.android.data.entity.FixtureEntity
import com.kansus.teammaker.android.data.entity.TeamEntity

data class FixtureWithTeams(
    @Embedded
    val fixture: FixtureEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "fixtureId",
        entity = TeamEntity::class
    ) val teams: List<TeamEntity>
)