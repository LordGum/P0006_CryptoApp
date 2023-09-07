package com.example.cryptoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.pojo.CoinInfoDetail

@Dao
interface CoinInfoDetailDao {

    @Query("SELECT * FROM coin_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinInfoDetail>>

    @Query("SELECT * FROM coin_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinInfoDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinInfoDetail>)

}