package com.tiger.wongnaicoinapp.data.model


import com.google.gson.annotations.SerializedName




class Stats {
    @SerializedName("total")
    val total = 0

    @SerializedName("offset")
    val offset = 0

    @SerializedName("limit")
    val limit = 0

    @SerializedName("order")
    val order: String? = null

    @SerializedName("base")
    val base: String? = null

    @SerializedName("totalMarkets")
    val totalMarkets = 0

    @SerializedName("totalExchanges")
    val totalExchanges = 0

    @SerializedName("totalMarketCap")
    val totalMarketCap = 0f

    @SerializedName("total24hVolume")
    val total24hVolume = 0f
}