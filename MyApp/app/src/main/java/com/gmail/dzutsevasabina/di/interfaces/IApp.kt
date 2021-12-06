package com.gmail.dzutsevasabina.di.interfaces

import android.content.Context
import com.gmail.dzutsevasabina.di.interfaces.IAppComponent

interface IApp {
    fun getAppComponent(context: Context): IAppComponent?
}
