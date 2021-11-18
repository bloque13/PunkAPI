package com.example.jetpackcompose.presentation.ui.beers.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.presentation.navigation.Screen
import com.example.jetpackcompose.presentation.ui.beers.components.BeerView
import com.example.jetpackcompose.presentation.ui.beers.state.BeersEvents
import com.example.jetpackcompose.presentation.ui.beers.viewmodels.BeersViewModel
import com.google.gson.Gson

@Composable
fun BeersScreen(
    onNavigateToScreen: (String) -> Unit,
    viewModel: BeersViewModel
) {
    val state = viewModel.state.value

    viewModel.onEvent(BeersEvents.Load)

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.beers) { beer ->
                BeerView(
                    beer = beer,
                    onItemClick = {
                        onNavigateToScreen("${Screen.BeerDetailScreen.route}/${Gson().toJson(it)}")
                    },
                    onFavouriteClick = {
                      viewModel.onEvent(BeersEvents.FavouriteBeer(it.toString()))
                    }
                )
            }
        }

        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}