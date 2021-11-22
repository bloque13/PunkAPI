package com.example.jetpackcompose.domain.repository

import android.content.SharedPreferences
import com.example.jetpackcompose.common.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesRepositoryImpl(
    val sharedPreferences: SharedPreferences
) :SharedPreferencesRepository {
    override fun save(favourites: ArrayList<Int>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(favourites)
        editor.putString(Constants.SAVED_FAVOURITES, json)
        editor.apply()
    }

    override fun take(): ArrayList<Int> {
        val gson = Gson()
        val json = sharedPreferences.getString(Constants.SAVED_FAVOURITES, null)
        val type = object : TypeToken<ArrayList<Int>>() {
        }.type

        if (json == null) {
            return ArrayList()
        } else {
            return gson.fromJson(json, type)
        }

    }
}