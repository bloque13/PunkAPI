package com.example.jetpackcompose.presentation.ui.beers.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.presentation.ui.beers.viewmodels.BeerDetailViewModel


@Composable
fun BeersDetailScreen(
    viewModel: BeerDetailViewModel
) {
    val state = viewModel.state.value

    state.beer?.let { beer ->

        Box(modifier = Modifier.fillMaxSize()) {
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = beer.name,
                        style = MaterialTheme.typography.caption
                    )
                    IconButton(
                        onClick = {
                            viewModel.favourite(beer.id.toString())
                        }
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            "favourited",
                            tint = if (beer.favourite) Color.Red else Color.Gray
                        )
                    }
                }
            }
        }
    }


}