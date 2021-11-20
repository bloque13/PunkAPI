package com.example.jetpackcompose.data.remote

import com.example.jetpackcompose.data.dto.Beer
import retrofit2.http.GET
import retrofit2.http.Path

interface PunkAPI {
    @GET("/v2/beers")
    suspend fun getBeers(): List<Beer>

    @GET("/v2/beers/{beerId}")
    suspend fun getBeer(@Path("beerId") beerId: Int): List<Beer>
}