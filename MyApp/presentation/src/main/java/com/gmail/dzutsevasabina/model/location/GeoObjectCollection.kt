package com.gmail.dzutsevasabina.model.location

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember")
    @Expose
    val featureMember: List<FeatureMember>
)
