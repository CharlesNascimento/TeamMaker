package com.kansus.teammaker.core

import com.kansus.teammaker.AndroidTest
import com.kansus.teammaker.android.Navigator
import org.junit.Before
import org.junit.Test


class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator

    @Before
    fun setup() {
        navigator = Navigator()
    }

    @Test
    fun `should forward user to login screen`() {
        /*whenever(authenticator.userLoggedIn()).thenReturn(false)

        navigator.showMain(activityContext())

        verify(authenticator).userLoggedIn()
        RouteActivity::class shouldNavigateTo LoginActivity::class*/
    }

    @Test
    fun `should forward user to movies screen`() {
        /*whenever(authenticator.userLoggedIn()).thenReturn(true)

        navigator.showMain(activityContext())

        verify(authenticator).userLoggedIn()
        RouteActivity::class shouldNavigateTo MoviesActivity::class*/
    }
}