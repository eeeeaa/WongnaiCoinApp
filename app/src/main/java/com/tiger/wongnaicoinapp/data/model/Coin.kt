package com.tiger.wongnaicoinapp.data.model
import com.google.gson.annotations.SerializedName

class Coin {
    @SerializedName("id")
    val id = 0

    @SerializedName("uuid")
    val uuid: String? = null

    @SerializedName("slug")
    val slug: String? = null

    @SerializedName("symbol")
    val symbol: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("color")
    val color: String? = null

    @SerializedName("iconType")
    val iconType: String? = null

    @SerializedName("iconUrl")
    val iconUrl: String? = null

    @SerializedName("websiteUrl")
    val websiteUrl: String? = null

    @SerializedName("socials")
    val socials: List<Social>? = null

    @SerializedName("links")
    val links: List<Link>? = null

    @SerializedName("confirmedSupply")
    val confirmedSupply = false

    @SerializedName("numberOfMarkets")
    val numberOfMarkets = 0

    @SerializedName("numberOfExchanges")
    val numberOfExchanges = 0

    @SerializedName("type")
    val type: String? = null

    @SerializedName("volume")
    val volume: Long = 0

    @SerializedName("marketCap")
    val marketCap: Long = 0

    @SerializedName("price")
    val price: String? = null

    @SerializedName("circulatingSupply")
    val circulatingSupply = 0f

    @SerializedName("totalSupply")
    val totalSupply = 0f

    @SerializedName("approvedSupply")
    val approvedSupply = false

    @SerializedName("firstSeen")
    val firstSeen: Long = 0

    @SerializedName("listedAt")
    val listedAt = 0

    @SerializedName("change")
    val change = 0f

    @SerializedName("rank")
    val rank = 0

    @SerializedName("history")
    val history: List<String>? = null

    @SerializedName("allTimeHigh")
    val allTimeHigh: AllTimeHigh? = null

    @SerializedName("penalty")
    val penalty = false

}