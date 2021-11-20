package com.example.jetpackcompose.data.dto


data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String? = null,
    val image_url: String,
    val abv: Double,
    var favourite: Boolean = false,
    val ingredients: Ingredients,
    val food_pairing: List<String>
)