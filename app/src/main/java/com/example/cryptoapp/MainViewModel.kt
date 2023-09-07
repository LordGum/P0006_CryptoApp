package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.database.AppDatabase
import com.example.cryptoapp.pojo.CoinInfoDetail
import com.example.cryptoapp.pojo.ObjectDataDetail
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application ): AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)
    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadPriceList()
    }

    private fun loadPriceList() {
        val disposable = ApiFactory.apiService.getTopListInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getDetailInfo(fsyms = it) }
            .map { getPriceListFromObjectDataDetail(it) }
            .delay(10,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    fun getDetailInfoAboutCoin (fsym: String) : LiveData<CoinInfoDetail> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fsym)
    }

    private fun getPriceListFromObjectDataDetail (detail: ObjectDataDetail) : List<CoinInfoDetail> {
        val resultList = ArrayList<CoinInfoDetail>()

        val jsonObject = detail.objectDetailDataJsonObject ?: return resultList

        val keySet = jsonObject.keySet()
        for(coinKey in keySet) {
            val currentJsonObject = jsonObject.getAsJsonObject(coinKey)
            val currentKeySet = currentJsonObject.keySet()
            for(currentKey in currentKeySet) {
                val onePriceInfo = Gson().fromJson(
                    currentJsonObject.getAsJsonObject(currentKey),
                    CoinInfoDetail::class.java
                )
                resultList.add(onePriceInfo)
            }
        }

        return resultList
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}