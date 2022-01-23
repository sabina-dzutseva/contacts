package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeoObject (
    @SerializedName("Point")
    @Expose
    val point: Point,

    @SerializedName("metaDataProperty")
    @Expose
    val metaData: MetaData
)
