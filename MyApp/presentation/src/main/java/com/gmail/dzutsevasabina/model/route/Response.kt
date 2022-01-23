package com.gmail.dzutsevasabina.model.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("routes")
    @Expose
    val routes: List<Route>,

    @SerializedName("status")
    @Expose
    val status: String
)
