package com.gmail.dzutsevasabina.di.viewmodel

import android.content.Context
import androidx.room.Room
import com.gmail.dzutsevasabina.db.Database
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun providesDatabase(context: Context) = Room.databaseBuilder(
        context,
        Database::class.java, "Locations"
    ).build()
}
