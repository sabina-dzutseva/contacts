package com.gmail.dzutsevasabina.di.viewmodel

import android.content.Context
import com.gmail.dzutsevasabina.interactor.ContactModel
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import com.gmail.dzutsevasabina.viewmodel.AlarmHandler
import dagger.Module
import dagger.Provides

@Module
class ContactInteractorModule {
    @Provides
    fun provideContactInteractor(repository: ContactRepositoryImpl): ContactModel {
        return ContactModel(repository)
    }
}
