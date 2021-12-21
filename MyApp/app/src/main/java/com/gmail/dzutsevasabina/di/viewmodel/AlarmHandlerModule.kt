package com.gmail.dzutsevasabina.di.viewmodel.details

import android.content.Context
import com.gmail.dzutsevasabina.viewmodel.AlarmHandler
import dagger.Module
import dagger.Provides

@Module
class AlarmHandlerModule {
    @Provides
    fun provideAlarmHandler(context: Context): AlarmHandler {
        return AlarmHandler(context)
    }
}
