package com.example.jetpackcompose.di

import com.example.jetpackcompose.data.remote.PunkAPI
import com.example.jetpackcompose.interactors.beers.GetBeerUseCase
import com.example.jetpackcompose.interactors.beers.GetBeersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideBeersUseCase(
        api: PunkAPI,
    ): GetBeersUseCase {
        return GetBeersUseCase(
            api = api,
        )
    }
    @ViewModelScoped
    @Provides
    fun provideBeerUseCase(
        api: PunkAPI,
    ): GetBeerUseCase {
        return GetBeerUseCase(
            api = api,
        )
    }
}