package com.gmail.dzutsevasabina.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.gmail.dzutsevasabina.model.ContactLocationEntity

@Dao
interface LocationDao {
    @Query("SELECT * FROM Location")
    fun getAll(): List<ContactLocationEntity>

    @Query("SELECT * FROM Location WHERE contact_id LIKE :contactId")
    fun findByContactId(contactId: Int): ContactLocationEntity?

    @Delete
    fun delete(location: ContactLocationEntity)

    @Insert(entity = ContactLocationEntity::class, onConflict = REPLACE)
    fun insert(location: ContactLocationEntity)
}
