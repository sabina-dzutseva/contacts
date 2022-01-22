package com.gmail.dzutsevasabina.di.viewmodel

import android.content.Context
import com.gmail.dzutsevasabina.db.Database
import com.gmail.dzutsevasabina.interactor.AddressRepository
import com.gmail.dzutsevasabina.interactor.ContactRepository
import com.gmail.dzutsevasabina.interactor.LocationRepository
import com.gmail.dzutsevasabina.interactor.RouteRepository
import com.gmail.dzutsevasabina.network.AddressService
import com.gmail.dzutsevasabina.network.RouteService
import com.gmail.dzutsevasabina.repository.AddressRepositoryImpl
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import com.gmail.dzutsevasabina.repository.LocationRepositoryImpl
import com.gmail.dzutsevasabina.repository.RouteRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideContactRepository(context: Context): ContactRepository {
        return ContactRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(database: Database): LocationRepository {
        return LocationRepositoryImpl(database)
    }

    @Singleton
    @Provides
    fun provideAddressRepository(service: AddressService): AddressRepository {
        return AddressRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideRouteRepository(service: RouteService): RouteRepository {
        return RouteRepositoryImpl(service)
    }
}
