package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.network.AddressService
import com.gmail.dzutsevasabina.network.RouteService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val GEOPOS_BASE_URL = "https://geocode-maps.yandex.ru/1.x/"
private const val DIRECTIONS_BASE_URL = "https://maps.googleapis.com/maps/api/directions/"

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesGson() = Gson()

    @Singleton
    @Provides
    fun providesClient() = OkHttpClient()

    @Named("map")
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(GEOPOS_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideGeopositionRetrofitService(@Named("map") retrofit: Retrofit): AddressService {
        return retrofit.create(AddressService::class.java)
    }

    @Singleton
    @Provides
    @Named("route")
    fun provideDirectionRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(DIRECTIONS_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideDirectionRetrofitService(@Named("route") retrofit: Retrofit): RouteService {
        return retrofit.create(RouteService::class.java)
    }
}
