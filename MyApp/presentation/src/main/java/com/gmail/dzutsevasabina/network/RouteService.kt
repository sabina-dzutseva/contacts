package com.gmail.dzutsevasabina.network

import com.gmail.dzutsevasabina.DIRECTIONS_API_KEY
import com.gmail.dzutsevasabina.model.route.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteService {
    @GET("json?key=$DIRECTIONS_API_KEY")
    fun getRoute(
        @Query("origin") origin: String?,
        @Query("destination") destination: String?
    ): Call<Response>
}
