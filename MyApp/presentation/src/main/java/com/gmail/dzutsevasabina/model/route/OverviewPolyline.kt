package com.gmail.dzutsevasabina.model.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points")
    @Expose
    val points: String
)
