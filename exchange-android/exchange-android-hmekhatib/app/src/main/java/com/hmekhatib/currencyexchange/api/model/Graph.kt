package com.hmekhatib.currencyexchange.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Graph{
    @SerializedName("x")
    var xaxis: List<String>? = null
    @SerializedName("buy")
    var yaxisB: List<Float>? = null
    @SerializedName("sell")
    var yaxisS: List<Float>? = null

}