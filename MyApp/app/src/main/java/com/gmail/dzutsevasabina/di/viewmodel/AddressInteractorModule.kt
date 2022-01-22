package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.interactor.AddressInteractor
import com.gmail.dzutsevasabina.interactor.AddressModel
import com.gmail.dzutsevasabina.interactor.AddressRepository
import dagger.Module
import dagger.Provides

@Module
class AddressInteractorModule {
    @Provides
    fun provideAddressInteractor(repository: AddressRepository): AddressInteractor {
        return AddressModel(repository)
    }
}
