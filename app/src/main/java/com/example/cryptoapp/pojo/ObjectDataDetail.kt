package com.example.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ObjectDataDetail (

    @SerializedName("RAW")
    @Expose
    val objectDetailDataJsonObject: JsonObject?
)