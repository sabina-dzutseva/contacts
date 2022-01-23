package com.gmail.dzutsevasabina.di.viewmodel.details

import android.content.Context
import com.gmail.dzutsevasabina.interactor.AlarmService
import com.gmail.dzutsevasabina.viewmodel.AlarmServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AlarmServiceModule {
    @Provides
    fun provideAlarmService(context: Context): AlarmService {
        return AlarmServiceImpl(context)
    }
}
