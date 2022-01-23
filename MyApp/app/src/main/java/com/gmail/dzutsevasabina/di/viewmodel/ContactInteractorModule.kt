package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.interactor.ContactInteractor
import com.gmail.dzutsevasabina.interactor.ContactModel
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ContactInteractorModule {
    @Provides
    fun provideContactInteractor(repository: ContactRepositoryImpl): ContactInteractor {
        return ContactModel(repository)
    }
}
