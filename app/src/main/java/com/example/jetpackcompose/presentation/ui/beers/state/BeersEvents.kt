package com.example.jetpackcompose.presentation.ui.beers.state

sealed class BeersEvents {

    object Load: BeersEvents()

    object LoadBeers: BeersEvents()

    object LoadFavouriteBeers: BeersEvents()

    data class FavouriteBeer(val favID: String) : BeersEvents()

}