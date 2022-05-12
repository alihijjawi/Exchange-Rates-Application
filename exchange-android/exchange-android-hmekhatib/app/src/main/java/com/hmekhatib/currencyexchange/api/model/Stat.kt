package com.hmekhatib.currencyexchange.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Stat {
    @SerializedName("1h")
    var OneHChange: Float? = null
    @SerializedName("12h")
    var TwelveHChange: Float? = null
    @SerializedName("24h")
    var TwentyFourHChange: Float? = null
    @SerializedName("7d")
    var SevenDChange: Float? = null
    @SerializedName("max")
    var maxRate: Float? = null
    @SerializedName("min")
    var minRate: Float? = null
}