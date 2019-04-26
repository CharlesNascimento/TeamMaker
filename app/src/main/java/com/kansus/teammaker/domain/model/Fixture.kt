package com.kansus.teammaker.domain.model

import java.util.*

data class Fixture(
    var id: Int = 0,
    var date: Date,
    var gameId: Int,
    val teams: List<Team>
)