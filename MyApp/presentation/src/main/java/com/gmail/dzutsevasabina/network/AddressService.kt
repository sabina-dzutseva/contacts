package com.gmail.dzutsevasabina.network

import com.gmail.dzutsevasabina.FORMAT
import com.gmail.dzutsevasabina.GEOCODE_API_KEY
import com.gmail.dzutsevasabina.model.location.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressService {
    @GET("?format=$FORMAT&apikey=$GEOCODE_API_KEY")
    fun getAddress(@Query("geocode") latlng: String?): Call<Response>
}
