package com.example.jetpackcompose.presentation.ui.beers.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.data.dto.Beer

@Composable
fun BeerView(
    beer: Beer,
    onItemClick: (Beer) -> Unit,
) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clickable {
                    onItemClick(beer)
                },
            contentColor = Color.Black
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Column {
                    BeerImage(url = beer.image_url, contentDescription = "Beer Image")
                }

                Spacer(Modifier.width(24.dp))

                Column {
                    Text(
                        text = beer.name,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = beer.tagline,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
}

