package com.example.cryptoapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptoapp.ApiFactory.IMAGE_BASE_URL
import com.example.cryptoapp.convertStampToTime
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coin_list")
data class CoinInfoDetail (
    @SerializedName("TYPE")
    @Expose
    val type: String?,
    @SerializedName("MARKET")
    @Expose
    val market: String?,

    @PrimaryKey
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromSymbol: String,

    @SerializedName("TOSYMBOL")
    @Expose
    val toSymbol: String?,
    @SerializedName("PRICE")
    @Expose
    val price: Double?,
    @SerializedName("LASTUPDATE")
    @Expose
    val lastUpdate: Long?,
    @SerializedName("LASTMARKET")
    @Expose
    val lastMarket: String?,
    @SerializedName("IMAGEURL")
    @Expose
    val imageUrl: String?,

    @SerializedName("HIGHDAY")
    @Expose
    val highDay: String?,
    @SerializedName("LOWDAY")
    @Expose
    val lowDay: String?
) {
    fun getFormattedTime(): String {
        return convertStampToTime(lastUpdate)
    }

    fun getFullImageUrl(): String {
        return String.format("%s%s", IMAGE_BASE_URL, imageUrl)
    }
}