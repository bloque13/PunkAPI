package com.example.jetpackcompose.interactors.beers

import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.data.remote.PunkAPI
import com.example.jetpackcompose.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetBeerUseCase(
    private val api: PunkAPI,
) {
    fun execute(beerId: Int
    ): Flow<DataState<Beer>> = flow {
        try {
            emit(DataState.loading())

            val beer = getBeer(beerId = beerId)

            if (beer != null) {
                emit(DataState.success(beer))
            } else {
                throw Exception("Unable to get beers.")
            }

        } catch (e: Exception) {
            emit(DataState.error<Beer>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getBeer(beerId: Int): Beer {
        return api.getBeer(beerId = beerId)[0]
    }
}