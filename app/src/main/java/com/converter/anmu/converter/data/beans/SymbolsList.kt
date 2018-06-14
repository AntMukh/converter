package com.converter.anmu.converter.data.beans

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SymbolsList {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("symbols")
    @Expose
    var symbols: JsonObject? = null

}
