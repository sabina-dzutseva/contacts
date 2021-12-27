package com.gmail.dzutsevasabina.di.viewmodel

import android.content.Context
import com.gmail.dzutsevasabina.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideContactRepository(context: Context): ContactRepositoryImpl {
        return ContactRepositoryImpl(context)
    }
}
