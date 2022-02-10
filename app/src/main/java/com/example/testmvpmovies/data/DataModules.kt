package com.example.testmvpmovies.data

import com.example.testmvpmovies.data.network.InternetClient
import com.example.testmvpmovies.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModules {

    @Provides
    @Singleton
    fun provideDataManager(client: InternetClient) = DataManager(client)

    @Provides
    @Singleton
    fun provideInternetClient(): InternetClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return InternetClient(retrofit)
    }

}