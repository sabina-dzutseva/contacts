package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Contact
import com.gmail.dzutsevasabina.entity.Location
import com.gmail.dzutsevasabina.entity.Route

interface RouteInteractor {
    fun getContactsWithLocation(): List<Contact>
    fun getLocation(id: Int): Location?
    fun getAllLocations(): List<Location>
    fun getRoutes(
        originLat: String,
        originLng: String,
        destLat: String,
        destLng: String
    ): List<Route>?
}
