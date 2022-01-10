package com.gmail.dzutsevasabina.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.dzutsevasabina.model.ContactLocationEntity

@Database(entities = [ContactLocationEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
