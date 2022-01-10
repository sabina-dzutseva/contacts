package com.gmail.dzutsevasabina.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location")
data class ContactLocationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id") val locationId: Int,
    @ColumnInfo(name = "contact_id") val contactId: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double
)
