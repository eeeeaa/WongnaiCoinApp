package com.tiger.wongnaicoinapp.domain.contract

import com.tiger.wongnaicoinapp.domain.entity.DomainCoinResponse
import io.reactivex.Observable

interface GetCoinListInterface {
    fun getCoinList(limit:Int?, offset:Int?, prefix:String?, symbols:String?, slugs:String?, ids:String?):Observable<DomainCoinResponse>
}