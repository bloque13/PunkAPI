package com.example.jetpackcompose.interactors.prefs

import com.example.jetpackcompose.domain.repository.SharedPreferencesRepository

class SharedPreferencesUseCaseImpl(
    val sharedPreferencesRepository: SharedPreferencesRepository
) : SharedPreferencesUseCase {
    override fun save(favourites: ArrayList<Int>) {
        return sharedPreferencesRepository.save(favourites)
    }

    override fun take(): ArrayList<Int> {
        return sharedPreferencesRepository.take()
    }
}