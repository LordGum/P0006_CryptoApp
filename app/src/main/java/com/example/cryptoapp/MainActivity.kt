package com.example.cryptoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.adapters.MainAdapter
import com.example.cryptoapp.pojo.CoinInfoDetail
import kotlinx.android.synthetic.main.activity_main.Progress_bar
import kotlinx.android.synthetic.main.activity_main.rvCoinPriceList

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter(this)
        rvCoinPriceList.adapter = adapter
        adapter.onItemClickListener = object : MainAdapter.OnItemClickListener {
            override fun onClick(coinInfoDetail: CoinInfoDetail) {
                val intent = CoinDetailActivity.newIntent(
                    this@MainActivity,
                    coinInfoDetail.fromSymbol
                )
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
            if(it != null) {
                Progress_bar.visibility =  View.INVISIBLE
            }
        })
    }

}