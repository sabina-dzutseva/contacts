package com.gmail.dzutsevasabina.di.app

import android.app.Application
import android.content.Context
import com.gmail.dzutsevasabina.di.interfaces.IApp
import com.gmail.dzutsevasabina.di.interfaces.IAppComponent

class App : Application(), IApp {
    private var appComponent: AppComponent? = null

    override fun getAppComponent(context: Context): IAppComponent? {
        if (appComponent == null) {
            initDependencies(context)
        }
        return appComponent
    }

    private fun initDependencies(context: Context) {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(context)).build()
    }
}
