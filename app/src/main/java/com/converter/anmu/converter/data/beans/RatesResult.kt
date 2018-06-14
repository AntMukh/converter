package com.converter.anmu.converter.data.beans

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RatesResult {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: Long? = null
    @SerializedName("base")
    @Expose
    var base: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("rates")
    @Expose
    var rates: JsonObject? = null

    var localUpdateTime: Long? = null

}
