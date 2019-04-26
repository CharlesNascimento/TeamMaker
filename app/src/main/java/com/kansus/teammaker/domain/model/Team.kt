package com.kansus.teammaker.domain.model

import java.util.*

data class Team(
    var id: Int = 0,
    var date: Date,
    var fixtureId: Int,
    val players: List<Player>
)