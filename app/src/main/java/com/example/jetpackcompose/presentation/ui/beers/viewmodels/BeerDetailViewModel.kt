package com.example.jetpackcompose.presentation.ui.beers.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.common.Constants
import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.presentation.ui.beers.state.BeerDetailState
import com.example.jetpackcompose.presentation.ui.beers.state.BeersState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private var favourites: MutableSet<String>? = null

    val state: MutableState<BeerDetailState> = mutableStateOf(BeerDetailState())

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        favourites = sharedPreferences.getStringSet(Constants.SAVED_FAVOURITES, mutableSetOf())
    }

    fun favourite(favID: String) {
        val beer = state.value.beer
        if (favourites?.contains(favID) == true) {
            favourites?.remove(favID)
            beer?.favourite = false
            state.value = state.value.copy(beer = beer)
        } else {
            favourites?.add(favID)
            beer?.favourite = true
            state.value = state.value.copy(beer = beer)
        }
        saveFavorites()
    }

    private fun saveFavorites() {
        sharedPreferences.edit()
            .putStringSet(Constants.SAVED_FAVOURITES, favourites)
            .apply()
    }

    fun setBeer123(beer: Beer) {
        this.state.value = state.value.copy(beer = beer)
    }

}