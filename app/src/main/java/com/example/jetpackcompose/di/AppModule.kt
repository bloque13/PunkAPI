package com.example.jetpackcompose.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.jetpackcompose.common.Constants
import com.example.jetpackcompose.data.remote.PunkAPI
import com.example.jetpackcompose.data.repository.PunkRepositoryImpl
import com.example.jetpackcompose.domain.repository.PunkRepository
import com.example.jetpackcompose.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providePunkAPI(): PunkAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PunkAPI::class.java)
    }

    @Provides
    @Singleton
    fun providePunkRepository(api: PunkAPI): PunkRepository {
        return PunkRepositoryImpl(api)
    }

}