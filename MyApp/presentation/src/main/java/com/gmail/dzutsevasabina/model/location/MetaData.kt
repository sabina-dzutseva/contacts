package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MetaData (
    @SerializedName("GeocoderMetaData")
    @Expose
    val geocoderMetaData: GeocoderMetaData
)
