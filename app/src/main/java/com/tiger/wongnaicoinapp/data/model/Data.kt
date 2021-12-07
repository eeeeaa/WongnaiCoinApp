package com.tiger.wongnaicoinapp.data.model

import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("stats")
    val stats: Stats? = null

    @SerializedName("base")
    val base: Base? = null

    @SerializedName("coins")
    val coins: List<Coin>? = null
}