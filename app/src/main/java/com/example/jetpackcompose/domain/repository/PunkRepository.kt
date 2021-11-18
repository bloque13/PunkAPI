package com.example.jetpackcompose.domain.repository

import com.example.jetpackcompose.data.dto.Beer

interface PunkRepository {

    suspend fun getBeers(): List<Beer>

}