package com.example.jetpackcompose.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object BeersScreen : Screen("beers_screen")

    object BeerDetailScreen: Screen("beer_detail_screen")

}
