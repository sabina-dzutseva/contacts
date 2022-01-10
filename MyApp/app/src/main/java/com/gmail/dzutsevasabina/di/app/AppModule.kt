package com.gmail.dzutsevasabina.di.app

import dagger.Module
import javax.inject.Singleton
import android.content.Context
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}
