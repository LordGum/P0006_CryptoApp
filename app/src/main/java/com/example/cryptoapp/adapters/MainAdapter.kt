package com.example.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinInfoDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*

class MainAdapter(private val context: Context): RecyclerView.Adapter<MainAdapter.MainInfoViewHolder>() {
    var coinInfoList: List<CoinInfoDetail> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return MainInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: MainInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]

        Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
        holder.tvSymbols.text = String.format(
            context.resources.getString(R.string.symbols_template),
            coin.fromSymbol,
            coin.toSymbol
        )
        holder.tvPrice.text = coin.price.toString()
        holder.tvLastUpdate.text = String.format(
            "%s%s",
            context.resources.getText(R.string.last_time),
            coin.getFormattedTime()
        )

        holder.itemView.setOnClickListener() {
            onItemClickListener?.onClick(coin)
        }
    }

    inner class MainInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin: ImageView = itemView.ivLogoCoin
        val tvSymbols: TextView = itemView.tvSymbols
        val tvPrice: TextView = itemView.tvPrice
        val tvLastUpdate: TextView = itemView.tvLastUpdate
    }

    interface OnItemClickListener{
        fun onClick(coinInfoDetail: CoinInfoDetail)
    }
}