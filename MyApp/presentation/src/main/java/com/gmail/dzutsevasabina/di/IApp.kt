package com.gmail.dzutsevasabina.di

import android.content.Context

interface IApp {
    fun getAppComponent(context: Context): IAppComponent?
}
