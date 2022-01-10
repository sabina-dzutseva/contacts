package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Location

class LocationModel(private val locationRepository: LocationRepository) : LocationInteractor {
    override fun getLocation(id: Int): Location? {
        return locationRepository.getLocation(id)
    }

    override fun getAllLocations(): List<Location> {
        return locationRepository.getAllLocations()
    }

    override fun insertLocation(location: Location) {
        locationRepository.insertLocation(location)
    }

    override fun deleteLocation(location: Location) {
        locationRepository.deleteLocation(location)
    }
}
