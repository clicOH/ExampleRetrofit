package com.example.regrofit.navigation

import kotlinx.serialization.Serializable


@Serializable
object NavHomeScreen

@Serializable
data class NavLocationScreen(val personId: Int)

@Serializable
data class NavEpisodeScreen(val episodeId: Int)

