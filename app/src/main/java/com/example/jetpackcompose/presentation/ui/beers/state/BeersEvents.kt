package com.example.jetpackcompose.presentation.ui.beers.state

sealed class BeersEvents {

    object LoadBeers: BeersEvents()

    data class FavouriteBeer(val favID: Int) : BeersEvents()

}