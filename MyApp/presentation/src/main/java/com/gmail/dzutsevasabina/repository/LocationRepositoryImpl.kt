package com.gmail.dzutsevasabina.repository

import com.gmail.dzutsevasabina.db.Database
import com.gmail.dzutsevasabina.entity.Location
import com.gmail.dzutsevasabina.interactor.LocationRepository
import com.gmail.dzutsevasabina.model.ContactLocationEntity
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(database: Database) : LocationRepository {

    private val dao = database.locationDao()

    override fun getLocation(id: Int): Location? {
        val location = dao.findByContactId(id) ?: return null
        return Location(id, location.latitude, location.longitude, location.address)
    }

    override fun getAllLocations(): List<Location> {
        return dao.getAll().map {
            Location(it.contactId, it.latitude, it.longitude, it.address)
        }
    }

    override fun insertLocation(location: Location) {
        dao.insert(ContactLocationEntity(0, location.contactId, location.address, location.longitude, location.latitude))
    }

    override fun deleteLocation(location: Location) {
        dao.findByContactId(location.contactId)?.let { dao.delete(it) }
    }
}
