package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.interactor.RouteInteractor
import com.gmail.dzutsevasabina.interactor.RouteModel
import com.gmail.dzutsevasabina.interactor.RouteRepository
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import com.gmail.dzutsevasabina.repository.LocationRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RouteInteractorModule {
    @Provides
    fun provideRouteInteractor(
        locationRepository: LocationRepositoryImpl,
        contactRepository: ContactRepositoryImpl,
        routeRepository: RouteRepository
    ): RouteInteractor {
        return RouteModel(locationRepository, contactRepository, routeRepository)
    }
}
