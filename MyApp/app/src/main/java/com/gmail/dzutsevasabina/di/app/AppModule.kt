package com.gmail.dzutsevasabina.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}
