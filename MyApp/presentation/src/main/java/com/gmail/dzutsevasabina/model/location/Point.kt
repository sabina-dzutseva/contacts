package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("pos")
    val pos: String
)
