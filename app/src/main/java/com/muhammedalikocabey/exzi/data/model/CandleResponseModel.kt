package com.muhammedalikocabey.exzi.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.muhammedalikocabey.exzi.core.utils.toPairName
import com.muhammedalikocabey.exzi.core.utils.toReadableDate

data class CandleResponseModel(
    @SerializedName("low")
    val low: Long? = null,
    @SerializedName("high")
    val high: Long? = null,
    @SerializedName("volume")
    val volume: Long? = null,
    @SerializedName("time")
    val time: Long? = null,
    @SerializedName("open")
    val open: Long? = null,
    @SerializedName("close")
    val close: Long? = null,
    @SerializedName("pair_id")
    val pairId: Int? = null,
    @SerializedName("times")
    val times: Times? = null,
    @SerializedName("low_f")
    val lowF: String? = null,
    @SerializedName("high_f")
    val highF: String? = null,
    @SerializedName("open_f")
    val openF: String? = null,
    @SerializedName("close_f")
    val closeF: String? = null,
    @SerializedName("volume_f")
    val volumeF: String? = null
) : Parcelable {
    data class ViewEntity(
        val low: Long? = null,
        val high: Long? = null,
        val volume: Long? = null,
        val time: String? = null,
        val open: Long? = null,
        val close: Long? = null,
        val pairId: String? = null,
        val times: Times.ViewEntity? = null,
        val lowF: String? = null,
        val highF: String? = null,
        val openF: String? = null,
        val closeF: String? = null,
        val volumeF: String? = null
    )

    fun toViewEntity(): ViewEntity {
        return ViewEntity(
            low = low,
            high = high,
            volume = volume,
            time = time.toReadableDate(),
            open = open,
            close = close,
            pairId = pairId.toPairName(),
            times = times?.toViewEntity(),
            lowF = lowF,
            highF = highF,
            openF = openF,
            closeF = closeF,
            volumeF = volumeF
        )
    }

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Times::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(low)
        parcel.writeValue(high)
        parcel.writeValue(volume)
        parcel.writeValue(time)
        parcel.writeValue(open)
        parcel.writeValue(close)
        parcel.writeValue(pairId)
        parcel.writeParcelable(times, flags)
        parcel.writeString(lowF)
        parcel.writeString(highF)
        parcel.writeString(openF)
        parcel.writeString(closeF)
        parcel.writeString(volumeF)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CandleResponseModel> {
        override fun createFromParcel(parcel: Parcel): CandleResponseModel {
            return CandleResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<CandleResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Times(
    @SerializedName("close")
    val close: Long? = null,
    @SerializedName("open")
    val open: Long? = null
) : Parcelable {
    data class ViewEntity(
        val close: String,
        val open: String
    )

    fun toViewEntity(): ViewEntity {
        return ViewEntity(
            close = close.toReadableDate(),
            open = open.toReadableDate()
        )
    }

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(close)
        parcel.writeValue(open)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Times> {
        override fun createFromParcel(parcel: Parcel): Times {
            return Times(parcel)
        }

        override fun newArray(size: Int): Array<Times?> {
            return arrayOfNulls(size)
        }
    }
}
