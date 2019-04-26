package com.kansus.teammaker.android

import android.content.Context
import android.view.View
import com.kansus.teammaker.domain.model.Game
import javax.inject.Singleton

@Singleton
class Navigator {

    fun showGameDetails(
        context: Context,
        game: Game,
        navigationExtras: Extras
    ) {
    }// = context.startActivity(LoginActivity.callingIntent(context))


    class Extras(val transitionSharedElement: View)
}