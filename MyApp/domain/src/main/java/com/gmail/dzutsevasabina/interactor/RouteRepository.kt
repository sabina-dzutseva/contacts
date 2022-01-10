package com.gmail.dzutsevasabina.interactor

import com.gmail.dzutsevasabina.entity.Route

interface RouteRepository {
    fun getRoutes(
        originLat: String,
        originLng: String,
        destLat: String,
        destLng: String
    ): List<Route>?
}
