package com.hmekhatib.currencyexchange.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Post {
    @SerializedName("added_date")
    var addedDate: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("user_id")
    var userid: Int? = null
    @SerializedName("usd_amount")
    var usdAmount: Float? = null
    @SerializedName("lbp_amount")
    var lbpAmount: Float? = null
    @SerializedName("typeSell")
    var usdToLbp: Boolean? = null
    @SerializedName("username")
    var username: String? = null
    @SerializedName("postid")
    var postID: Int? = null

}