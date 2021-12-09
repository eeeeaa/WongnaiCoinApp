package com.tiger.wongnaicoinapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.tiger.wongnaicoinapp.data.contract.FetchAPIInterface
import com.tiger.wongnaicoinapp.data.datasource.FetchAPI
import com.tiger.wongnaicoinapp.domain.contract.GetCoinListInterface
import com.tiger.wongnaicoinapp.domain.entity.GetCoinList
import com.tiger.wongnaicoinapp.presentation.contract.CoinAPIInteractorInterface
import com.tiger.wongnaicoinapp.presentation.interactor.CoinAPIInteractor
import com.tiger.wongnaicoinapp.presentation.interactor.PresCoinResponse
import com.tiger.wongnaicoinapp.presentation.provider.NetworkProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers

class MainViewModel:ViewModel() {
    val urlSrc = "https://api.coinranking.com/v1/"
    val token = "coinranking3b3e67606ec6dbfa168aad2c4878d637dad5e762ad2e26c3"
    val fetchAPIInterface:FetchAPIInterface = FetchAPI(NetworkProvider(urlSrc,token).provideCoinAPIClient())
    val getCoinListInterface:GetCoinListInterface = GetCoinList(fetchAPIInterface)
    val coinAPIInteractorInterface:CoinAPIInteractorInterface = CoinAPIInteractor(getCoinListInterface)
    lateinit var currentData:LiveData<PresCoinResponse>
    fun getCoinData(limit:Int?,offset:Int?,prefix:String?,symbols:String?,slugs:String?,ids:String?){
        val publisher = coinAPIInteractorInterface.getCoinList(limit, offset, prefix, symbols, slugs, ids).toFlowable(BackpressureStrategy.BUFFER)
        if (publisher != null) {
            currentData = LiveDataReactiveStreams.fromPublisher(publisher.subscribeOn(Schedulers.io()))
        }
    }
}