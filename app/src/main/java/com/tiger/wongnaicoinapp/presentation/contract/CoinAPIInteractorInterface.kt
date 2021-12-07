package com.tiger.wongnaicoinapp.presentation.contract

import com.tiger.wongnaicoinapp.presentation.interactor.PresCoinResponse
import io.reactivex.Observable

interface CoinAPIInteractorInterface {
    fun getCoinList(limit:Int, offset:Int, prefix:String, symbols:String, slugs:String, ids:String):Observable<PresCoinResponse>
}