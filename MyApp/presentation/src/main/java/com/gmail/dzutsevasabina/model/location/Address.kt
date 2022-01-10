package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("formatted")
    @Expose
    val formattedAddress: String
)
