package com.gmail.dzutsevasabina.repository

import com.gmail.dzutsevasabina.entity.Route
import com.gmail.dzutsevasabina.interactor.RouteRepository
import com.gmail.dzutsevasabina.network.RouteService
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(var service: RouteService) : RouteRepository {
    override fun getRoutes(
        originLat: String,
        originLng: String,
        destLat: String,
        destLng: String
    ): List<Route>? {
        return service.getRoute("$originLat,$originLng", "$destLat,$destLng")
            .execute().body()?.routes?.map { Route(it.overviewPolyline.points) }
    }
}
