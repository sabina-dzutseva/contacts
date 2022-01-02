package com.gmail.dzutsevasabina.di.viewmodel

import com.gmail.dzutsevasabina.viewmodel.AlarmHandler
import com.gmail.dzutsevasabina.viewmodel.AlarmServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AlarmHandlerModule {
    @Provides
    fun provideAlarmHandler(alarmServiceImpl: AlarmServiceImpl): AlarmHandler {
        return AlarmHandler(alarmServiceImpl)
    }
}
