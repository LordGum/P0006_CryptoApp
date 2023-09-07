package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        if(!intent.hasExtra(FSYM)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(FSYM)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfoAboutCoin(it).observe(this, Observer {
                tvPrice.text = it.price.toString()
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.getFormattedTime()
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
            })
        }

    }

    companion object{
        private const val FSYM = "fsym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FSYM, fromSymbol)
            return intent
        }
    }
}