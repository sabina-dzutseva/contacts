package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.interactor.LocationInteractor
import com.gmail.dzutsevasabina.interactor.LocationModel
import com.gmail.dzutsevasabina.repository.LocationRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class LocationInteractorModule {
    @Provides
    fun provideLocationInteractor(repository: LocationRepositoryImpl): LocationInteractor {
        return LocationModel(repository)
    }
}
