package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Location

interface LocationRepository {
    fun getLocation(id: Int): Location?
    fun getAllLocations(): List<Location>
    fun insertLocation(location: Location)
    fun deleteLocation(location: Location)
}
