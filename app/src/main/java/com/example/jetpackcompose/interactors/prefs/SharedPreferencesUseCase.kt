package com.example.jetpackcompose.interactors.prefs

interface SharedPreferencesUseCase {
    fun save(favourites: ArrayList<Int>)
    fun take(): ArrayList<Int>
}