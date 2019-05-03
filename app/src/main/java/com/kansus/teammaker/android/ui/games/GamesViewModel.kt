package com.kansus.teammaker.android.ui.games

import androidx.lifecycle.MutableLiveData
import com.kansus.teammaker.android.core.BaseViewModel
import com.kansus.teammaker.core.UseCase
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.usecase.game.DeleteGameUseCase
import com.kansus.teammaker.domain.usecase.game.GetGamesUseCase
import javax.inject.Inject

class GamesViewModel @Inject constructor(
    private val getGamesUserCase: GetGamesUseCase,
    private var deleteGameUseCase: DeleteGameUseCase
) : BaseViewModel() {

    var games: MutableLiveData<List<Game>> = MutableLiveData()

    fun getGames() =
        getGamesUserCase(UseCase.None()) { it.either(::handleFailure, ::handleGames) }

    fun delete(game: Game) = deleteGameUseCase(game)

    private fun handleGames(games: List<Game>) {
        this.games.value = games
    }
}