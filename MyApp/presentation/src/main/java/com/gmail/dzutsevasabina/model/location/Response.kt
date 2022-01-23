package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("response")
    val response: GeoObjectResponse
)
