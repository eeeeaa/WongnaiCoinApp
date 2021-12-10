package com.tiger.wongnaicoinapp.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.tiger.wongnaicoinapp.R
import com.tiger.wongnaicoinapp.data.model.Coin
import androidx.core.text.HtmlCompat






class CoinListAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val coins:MutableList<Coin> = ArrayList()
    private val specialPos:Int = 5
    private val TAG = "CoinListAdapter"
    enum class Type(val typeIndex:Int){
        SPECIAL_TYPE(1),CONTENT_TYPE(0)
    }
    class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var coinImg: ImageView
        var coinName:TextView
        var coinDescription:TextView
        init {
            coinImg = itemView.findViewById(R.id.coin_image)
            coinName = itemView.findViewById(R.id.coin_name)
            coinDescription=itemView.findViewById(R.id.coin_description)
        }
    }
    class CoinSpecialHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var coinImg: ImageView
        var coinName:TextView
        init {
            coinImg = itemView.findViewById(R.id.coin_image)
            coinName = itemView.findViewById(R.id.coin_name)
        }
    }

    override fun getItemViewType(position: Int): Int {
        when{
            position in 0..4 -> {
                val temp = specialPos
                return if((position+1) % temp == 0 && position != 0){
                    //special position
                    Type.SPECIAL_TYPE.typeIndex
                }else{
                    //normal position
                    Type.CONTENT_TYPE.typeIndex
                }
            }
            position > 4  -> {
                return if((position+1) % specialPos == 0){
                    //special position
                    Type.SPECIAL_TYPE.typeIndex
                }else{
                    //normal position
                    Type.CONTENT_TYPE.typeIndex
                }
            }
            else -> {
                return Type.CONTENT_TYPE.typeIndex
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == Type.CONTENT_TYPE.typeIndex){
            val v = LayoutInflater.from(parent.context).inflate(R.layout.main_adapter_layout, parent, false)
            CoinViewHolder(v)
        }else{
            val v = LayoutInflater.from(parent.context).inflate(R.layout.main_adapter_layout_specialposition, parent, false)
            CoinSpecialHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(!coins.isNullOrEmpty()) {
            if (getItemViewType(position) == Type.CONTENT_TYPE.typeIndex) {
                val coin = coins[position]
                val viewHolder = holder as CoinViewHolder
                val imageLoader = ImageLoader.Builder(context)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .componentRegistry {
                        add(SvgDecoder(context))
                    }
                    .build()
                val request = ImageRequest.Builder(viewHolder.coinImg.context)
                    .data(coin.iconUrl)
                    .target(viewHolder.coinImg)
                    .build()
                imageLoader.enqueue(request)

                viewHolder.coinName.text = coin.name
                val plainTextFromHTML =
                    coin.description?.let {
                        HtmlCompat.fromHtml(
                            it,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString()
                    }
                if(plainTextFromHTML.isNullOrBlank())
                    viewHolder.coinDescription.text = "no information available"
                else{
                    viewHolder.coinDescription.text = plainTextFromHTML
                }
            } else if (getItemViewType(position) == Type.SPECIAL_TYPE.typeIndex) {
                val coin = coins[position]
                val viewHolder = holder as CoinSpecialHolder
                val imageLoader = ImageLoader.Builder(context)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .componentRegistry {
                        add(SvgDecoder(context))
                    }
                    .build()
                val request = ImageRequest.Builder(viewHolder.coinImg.context)
                    .data(coin.iconUrl)
                    .target(viewHolder.coinImg)
                    .build()
                imageLoader.enqueue(request)
                viewHolder.coinName.text = coin.name
            }
        }
    }

    override fun getItemCount(): Int {
        return coins.size
    }
    fun getCoins():Collection<Coin>{
        return coins
    }
    fun clear(){
        coins.clear()
    }
    fun addResults(results: Collection<Coin>?) {
        if(!results.isNullOrEmpty()){
           coins.addAll(results)
        }
    }
    fun add(coin:Coin) {
        coins.add(coin)
    }

    fun getItem(position: Int): Coin? {
        return coins[position]
    }


}