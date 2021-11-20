package com.example.jetpackcompose.presentation.ui.beers.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.interactors.beers.GetBeersUseCase
import com.example.jetpackcompose.presentation.ui.beers.state.BeersEvents
import com.example.jetpackcompose.presentation.ui.beers.state.BeersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,

    ) : ViewModel() {

    val state: MutableState<BeersState> = mutableStateOf(BeersState())

    init {
        onEvent(BeersEvents.LoadBeers)
    }

    fun onEvent(event: BeersEvents) {
        when (event) {
            is BeersEvents.LoadBeers -> {
                loadBeers()
            }
        }
    }

    private fun loadBeers() {
        getBeersUseCase.execute().onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.loading)
            dataState.data?.let { beers ->
                state.value = state.value.copy(beers = beers)
            }
        }.launchIn(viewModelScope)
    }

}