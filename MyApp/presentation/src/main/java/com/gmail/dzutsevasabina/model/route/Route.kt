package com.gmail.dzutsevasabina.model.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("overview_polyline")
    @Expose
    val overviewPolyline: OverviewPolyline
)
