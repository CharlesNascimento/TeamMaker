package com.kansus.teammaker.android.ui.games

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import com.kansus.teammaker.R
import com.kansus.teammaker.android.Navigator
import com.kansus.teammaker.android.core.BaseFragment
import com.kansus.teammaker.android.extension.*
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.core.exception.Failure.NetworkConnection
import com.kansus.teammaker.core.exception.Failure.ServerError
import com.kansus.teammaker.domain.model.Game
import kotlinx.android.synthetic.main.fragment_games.*
import javax.inject.Inject

/**
 *
 */
class GamesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var gamesAdapter: GamesAdapter

    private lateinit var viewModel: GamesViewModel

    override fun layoutId() = R.layout.fragment_games

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModel(viewModelFactory) {
            observe(games, ::showGames)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadGames()

        fab.setOnClickListener { Log.d("AAA", "CLICKED") }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun initializeView() {
        gamesAdapter = GamesAdapter(listOf()) { game, navigationExtras ->
            navigator.showGameDetails(activity!!, game, navigationExtras)
            Log.d("AAA", "Selected game ${game.id}")
        }

        with(recycler_view) {
            adapter = gamesAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadGames() {
        //emptyView.invisible()
        recycler_view?.visible()

        showProgress()
        viewModel.getGames()
    }

    private fun showGames(games: List<Game>?) {
        gamesAdapter.mValues = games.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.app_name)
            is ServerError -> renderFailure(R.string.app_name)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        recycler_view.invisible()
        //emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.app_name, ::loadGames)
    }
}
