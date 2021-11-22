package com.example.jetpackcompose.presentation.ui.beers.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.common.Constants
import com.example.jetpackcompose.interactors.beers.GetBeerUseCase
import com.example.jetpackcompose.interactors.prefs.SharedPreferencesUseCase
import com.example.jetpackcompose.presentation.ui.beers.state.BeerDetailState
import com.example.jetpackcompose.presentation.ui.beers.state.BeersEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val getBeerUseCase: GetBeerUseCase,
    private val sharedPreferencesUseCase: SharedPreferencesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var favourites = ArrayList<Int>()

    val state: MutableState<BeerDetailState> = mutableStateOf(BeerDetailState())

    init {
        loadFavourites()
        savedStateHandle.get<Int>(Constants.PARAM_BEER_ID)?.let { beerId ->
            loadBeer(beerId)
        }
    }

    private fun loadFavourites() {
        favourites = sharedPreferencesUseCase.take()
    }

    fun onEvent(event: BeersEvents) {
        when (event) {
            is BeersEvents.FavouriteBeer -> {
                favourite(favID = event.favID)
            }
        }
    }

    private fun favourite(favID: Int) {
        val beer = state.value.beer
        if (favourites.contains(favID)) {
            favourites.remove(favID)
        } else {
            favourites.add(favID)
        }
        saveFavorites()

        beer?.let { loadBeer(it.id) }
    }

    private fun saveFavorites() {
        sharedPreferencesUseCase.save(favourites)
    }

    private fun loadBeer(id: Int) {
        getBeerUseCase.execute(beerId = id).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.loading)
            dataState.data?.let { beer ->
                if (favourites.contains(beer.id)) {
                    beer.favourite = true
                }
                state.value = state.value.copy(beer = beer)
            }
        }.launchIn(viewModelScope)
    }

}