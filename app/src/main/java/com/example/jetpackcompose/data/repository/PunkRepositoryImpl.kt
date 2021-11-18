package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.data.remote.PunkAPI
import com.example.jetpackcompose.domain.repository.PunkRepository
import javax.inject.Inject


class PunkRepositoryImpl @Inject constructor(
    private val api: PunkAPI
) : PunkRepository {

    override suspend fun getBeers(): List<Beer> {
        return api.getBeers()
    }

}