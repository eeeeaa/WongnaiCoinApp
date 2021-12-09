package com.tiger.wongnaicoinapp.data.contract

import com.tiger.wongnaicoinapp.data.datasource.CoinResponse
import io.reactivex.Observable

interface FetchAPIInterface {
    fun getDataFromApi(limit:Int?, offset:Int?, prefix:String?, symbols:String?, slugs:String?, ids:String?):Observable<CoinResponse>
}