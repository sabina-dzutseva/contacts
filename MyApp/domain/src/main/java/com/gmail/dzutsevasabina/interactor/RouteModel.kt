package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Contact
import com.gmail.dzutsevasabina.entity.Location
import com.gmail.dzutsevasabina.entity.Route

class RouteModel(
    private val locationRepository: LocationRepository,
    private val contactRepository: ContactRepository,
    private val routeRepository: RouteRepository
) : RouteInteractor {
    override fun getContactsWithLocation(): List<Contact> {
        val locations = locationRepository.getAllLocations()
        return contactRepository.getContactList("").filter { contact ->
            locations.map { location -> location.contactId }.contains(contact.id)
        }
    }

    override fun getLocation(id: Int): Location? {
        return locationRepository.getLocation(id)
    }

    override fun getAllLocations(): List<Location> {
        return locationRepository.getAllLocations()
    }

    override fun getRoutes(
        originLat: String,
        originLng: String,
        destLat: String,
        destLng: String
    ): List<Route>? {
        return routeRepository.getRoutes(originLat, originLng, destLat, destLng)
    }
}
