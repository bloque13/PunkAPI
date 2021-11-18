package com.example.jetpackcompose.presentation.ui.beers.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.common.Constants
import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.interactors.beers.GetBeersUseCase
import com.example.jetpackcompose.presentation.ui.beers.state.BeersEvents
import com.example.jetpackcompose.presentation.ui.beers.state.BeersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getBeersUseCase: GetBeersUseCase,

    ) : ViewModel() {

    private var favourites: MutableSet<String>? = null

    val state: MutableState<BeersState> = mutableStateOf(BeersState())


    fun onEvent(event: BeersEvents) {
        when (event) {
            is BeersEvents.Load -> {
                load()
            }
            is BeersEvents.LoadBeers -> {
                loadBeers()
            }
            is BeersEvents.FavouriteBeer -> {
                favourite(event.favID)
            }
            is BeersEvents.LoadFavouriteBeers -> {
                loadFavourites()
            }
        }
    }

    fun load() {
        onEvent(BeersEvents.LoadFavouriteBeers)
        onEvent(BeersEvents.LoadBeers)
    }

    private fun loadFavourites() {
        favourites = sharedPreferences.getStringSet(Constants.SAVED_FAVOURITES, mutableSetOf())
    }

    private fun favourite(favID: String) {
        if (favourites?.contains(favID) == true) {
            favourites?.remove(favID)
        } else {
            favourites?.add(favID)
        }

        saveFavorites()
        loadBeers()
    }

    private fun saveFavorites() {
        sharedPreferences.edit()
            .putStringSet(Constants.SAVED_FAVOURITES, favourites)
            .apply()
    }

    private fun loadBeers() {
        getBeersUseCase.execute().onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.loading)
            dataState.data?.let { beers ->
                beers.forEach { beer ->
                    beer.favourite = favourites?.contains(beer.id.toString() ) ?: false
                }
                state.value = state.value.copy(beers = beers)
            }
        }.launchIn(viewModelScope)
    }

}