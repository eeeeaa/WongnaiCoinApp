package com.tiger.wongnaicoinapp.data.model

import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.Nullable

class Data {
    @SerializedName("stats")
    val stats: Stats? = null

    @SerializedName("base")
    val base: Base? = null

    @SerializedName("coins")
    @Nullable
    val coins: List<Coin>? = null
}