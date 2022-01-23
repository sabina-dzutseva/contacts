package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeocoderMetaData (
    @SerializedName("Address")
    @Expose
    val address: Address
)
