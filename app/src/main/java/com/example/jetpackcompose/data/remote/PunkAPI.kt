package com.example.jetpackcompose.data.remote

import com.example.jetpackcompose.data.dto.Beer
import retrofit2.http.GET

interface PunkAPI {
    @GET("/v2/beers")
    suspend fun getBeers(): List<Beer>
}