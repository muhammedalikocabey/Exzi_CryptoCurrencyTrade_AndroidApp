package com.muhammedalikocabey.exzi.data.model

import com.google.gson.annotations.SerializedName
import com.muhammedalikocabey.exzi.core.utils.toFormattedRateF
import com.muhammedalikocabey.exzi.core.utils.toFormattedVolumeF

data class OrderBookResponseModel(
    val buy: List<OrderBookEntry>,
    val sell: List<OrderBookEntry>
) {
    data class ViewEntity(
        val buy: List<OrderBookEntry.ViewEntity>? = null,
        val sell: List<OrderBookEntry.ViewEntity>? = null
    )

    fun toViewEntity(): ViewEntity {
        return ViewEntity(
            buy = buy.map { it.toViewEntity() },
            sell = sell.map { it.toViewEntity() }
        )
    }
}
data class OrderBookEntry(
    @SerializedName("volume")
    val volume: Long,
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Long,
    @SerializedName("price")
    val price: Long,
    @SerializedName("rate_f")
    val rateF: String,
    @SerializedName("volume_f")
    val volumeF: String
) {
    data class ViewEntity(
        val volume: String? = null,
        val count: String? = null,
        val rate: String? = null,
        val price: String? = null,
        val rateF: String? = null,
        val volumeF: String? = null
    )

    fun toViewEntity(): ViewEntity {
        return ViewEntity(
            volume = volume.toString(),
            count = count.toString(),
            rate = rate.toString(),
            price = price.toString(),
            rateF = rateF.toFormattedRateF(),
            volumeF = volumeF.toFormattedVolumeF()
        )
    }
}
