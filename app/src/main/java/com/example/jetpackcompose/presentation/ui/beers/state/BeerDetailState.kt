package com.example.jetpackcompose.presentation.ui.beers.state

import com.example.jetpackcompose.data.dto.Beer

data class BeerDetailState(
    val isLoading: Boolean = false,
    val beer: Beer? = null
)