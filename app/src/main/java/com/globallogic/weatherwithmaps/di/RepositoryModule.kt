package com.globallogic.weatherwithmaps.di

import com.globallogic.weatherwithmaps.data.remote.api.OpenWeatherApi
import com.globallogic.weatherwithmaps.domain.repository.WeatherRepository
import com.globallogic.weatherwithmaps.domain.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: OpenWeatherApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, ioDispatcher)
    }
}