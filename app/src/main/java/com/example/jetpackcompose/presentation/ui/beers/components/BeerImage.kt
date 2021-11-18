package com.example.jetpackcompose.presentation.ui.beers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.common.Constants.IMAGE_HEIGHT
import com.example.jetpackcompose.common.Constants.IMAGE_WIDTH
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun BeerImage(
    url: String,
    contentDescription: String,
){
    val painter = rememberCoilPainter(url)
    Box {
        Image(
            modifier = Modifier
                .width(IMAGE_WIDTH.dp)
                .height(IMAGE_HEIGHT.dp),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
        )
        when(painter.loadState){
            is ImageLoadState.Error -> { }
            is ImageLoadState.Success -> { }
            is ImageLoadState.Loading -> {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT.dp)
                    .width(IMAGE_WIDTH.dp)
                ) {
                    Text(text = "loading")
                }
            }
        }
    }
}