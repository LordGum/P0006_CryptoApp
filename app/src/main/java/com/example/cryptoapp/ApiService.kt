package com.example.cryptoapp

import com.example.cryptoapp.pojo.ObjectData
import com.example.cryptoapp.pojo.ObjectDataDetail
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT  = "limit"
        private const val QUERY_PARAM_TSYM = "tsym"

        private const val QUERY_PARAM_TSYMS = "tsyms"
        private const val QUERY_PARAM_FSYMS = "fsyms"

        private const val CURRENCY = "USD"
    }

    @GET("top/totalvolfull")
    fun getTopListInfo (
        @Query(API_KEY) api_key: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TSYM) tsym: String = CURRENCY
    ): Single<ObjectData>

    @GET("pricemultifull")
    fun getDetailInfo (
        @Query(API_KEY) api_key: String = "",
        @Query(QUERY_PARAM_TSYMS) tsym: String = CURRENCY,
        @Query(QUERY_PARAM_FSYMS) fsyms: String?,
    ): Single<ObjectDataDetail>
}