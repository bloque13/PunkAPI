package com.example.jetpackcompose.presentation.ui.beers.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.common.Constants
import com.example.jetpackcompose.interactors.beers.GetBeerUseCase
import com.example.jetpackcompose.presentation.ui.beers.state.BeerDetailState
import com.example.jetpackcompose.presentation.ui.beers.state.BeersEvents
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getBeerUseCase: GetBeerUseCase,
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

    fun onEvent(event: BeersEvents) {
        when (event) {
            is BeersEvents.FavouriteBeer -> {
                favourite(favID = event.favID)
            }
        }
    }

    private fun loadFavourites() {
        val gson = Gson()
        val json = sharedPreferences.getString(Constants.SAVED_FAVOURITES, null)
        val type = object : TypeToken<ArrayList<Int>>() {
        }.type

        if (json == null) {
            favourites = ArrayList()
        } else {
            favourites = gson.fromJson(json, type)
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
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(favourites)
        editor.putString(Constants.SAVED_FAVOURITES, json)
        editor.apply()
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