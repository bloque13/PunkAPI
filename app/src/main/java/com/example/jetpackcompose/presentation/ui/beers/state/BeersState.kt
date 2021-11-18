package com.example.jetpackcompose.presentation.ui.beers.state

import com.example.jetpackcompose.data.dto.Beer

data class BeersState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val query: String = "",
    val error: String = "",
    val beers: List<Beer> = listOf(),
    val selected: Boolean = false
    )