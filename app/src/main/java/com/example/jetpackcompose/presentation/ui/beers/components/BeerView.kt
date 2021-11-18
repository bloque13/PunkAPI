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
    favourite: Boolean = false,
    onItemClick: (Beer) -> Unit,
    onFavouriteClick: (Int) -> Unit = {},
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

//                Spacer(modifier = Modifier.weight(1.0f)) // fill height with spacer
//
//                        Icon(
//                            Icons.Filled.Favorite,
//                            "favourited",
//                            tint = if (beer.favourite) Color.Red else Color.Gray,
//                            modifier =  Modifier.weight(1.0f)
//                        )


            }


//        Column {
//            Text(
//                text = "${beer.name}.",
//                style = MaterialTheme.typography.body1,
//                overflow = TextOverflow.Ellipsis
//            )
//            Text(
//                text = "First Brewed: ${beer.first_brewed}",
//                textAlign = TextAlign.End,
//                style = MaterialTheme.typography.body2
//            )
//
//            Text(
//                text = if (beer.favourite) "Favorite" else "Not Fav",
//                textAlign = TextAlign.End,
//                style = MaterialTheme.typography.body2
//            )
//
//            Row {
//                Text(
//                    text = "Favorite: ",
//                    style = MaterialTheme.typography.body2
//                )
//
//                IconButton(onClick = {
//                    onFavouriteClick(beer.id)
//                }) {
//                    Icon(
//                        Icons.Filled.Favorite,
//                        "favourited",
//                        tint = if (beer.favourite) Color.Red else Color.Gray
//                    )
//                }
//
//            }
//
//        }

        }
}

