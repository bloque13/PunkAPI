package com.example.jetpackcompose.presentation.ui.beers.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.data.dto.Ingredient
import com.example.jetpackcompose.presentation.ui.beers.components.BeerImage
import com.example.jetpackcompose.presentation.ui.beers.state.BeersEvents
import com.example.jetpackcompose.presentation.ui.beers.viewmodels.BeerDetailViewModel

@Composable
fun BeersDetailScreen(
    viewModel: BeerDetailViewModel
) {
    val state = viewModel.state.value

    state.beer?.let { beer ->

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.padding(12.dp)) {

                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(10.dp))


                Row{
                    BeerImage(url = beer.image_url, contentDescription = "Beer Image")
                    
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            viewModel.onEvent(BeersEvents.FavouriteBeer(beer.id))
                        }
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            "favourited",
                            tint = if (beer.favourite) Color.Red else Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))


                beer.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                IngredientView(beer.ingredients.malt, "Malt")

                IngredientView(beer.ingredients.hops, "Hops")

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "FOOD PAIRINGS",
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(10.dp))

                beer.food_pairing.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientView(ingredients: List<Ingredient>, title: String) {

    Text(
        text = title,
        style = MaterialTheme.typography.h6
    )

    Spacer(modifier = Modifier.height(10.dp))

    ingredients.forEach { ingredient ->
        Row(
            modifier = Modifier.padding(5.dp)
        )
        {
            Text(
                text = "${ingredient.name} (${ingredient.amount.value} ${ingredient.amount.unit})",
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}