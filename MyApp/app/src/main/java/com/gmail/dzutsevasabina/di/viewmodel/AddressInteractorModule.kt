package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.interactor.*
import dagger.Module
import dagger.Provides

@Module
class AddressInteractorModule {
    @Provides
    fun provideAddressInteractor(repository: AddressRepository): AddressInteractor {
        return AddressModel(repository)
    }
}
