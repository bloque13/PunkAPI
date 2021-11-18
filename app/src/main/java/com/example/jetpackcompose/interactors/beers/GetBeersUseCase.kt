package com.example.jetpackcompose.interactors.beers

import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.data.remote.PunkAPI
import com.example.jetpackcompose.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetBeersUseCase(
    private val api: PunkAPI,
) {
    fun execute(
    ): Flow<DataState<List<Beer>>> = flow {
        try {
            emit(DataState.loading())

            val beers = getBeers()

            if (beers != null) {
                emit(DataState.success(beers))
            } else {
                throw Exception("Unable to get beers.")
            }

        } catch (e: Exception) {
            emit(DataState.error<List<Beer>>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getBeers(): List<Beer> {
        return api.getBeers()
    }
}