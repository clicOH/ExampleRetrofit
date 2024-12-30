package com.example.regrofit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.regrofit.ui.screens.episode.EpisodeScreen
import com.example.regrofit.ui.screens.home.HomeScreen
import com.example.regrofit.ui.screens.location.LocationScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavHomeScreen) {

        composable<NavHomeScreen> {
            HomeScreen(onPersonClick = { personId ->
                navController.navigate(NavLocationScreen(personId))
            })
        }

        composable<NavEpisodeScreen> {
            EpisodeScreen(navController)
        }

        composable<NavLocationScreen> {
            val personId = it.toRoute<NavLocationScreen>().personId
            LocationScreen(onLocationClick = {
                navController.popBackStack()
            }, personId)
        }


    }
}