package com.example.jetpackcompose.domain.repository

interface SharedPreferencesRepository {
    fun save(favourites: ArrayList<Int>)
    fun take(): ArrayList<Int>
}