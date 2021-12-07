package com.tiger.wongnaicoinapp.domain.entity

import android.util.Log
import com.tiger.wongnaicoinapp.data.contract.FetchAPIInterface
import com.tiger.wongnaicoinapp.data.datasource.CoinError
import com.tiger.wongnaicoinapp.data.datasource.CoinResponseFailure
import com.tiger.wongnaicoinapp.data.datasource.CoinResponseSuccess
import com.tiger.wongnaicoinapp.domain.contract.GetCoinListInterface
import io.reactivex.Observable
import io.reactivex.android.BuildConfig
import io.reactivex.schedulers.Schedulers

class GetCoinList(private val fetchAPIInterface: FetchAPIInterface):GetCoinListInterface {
    override fun getCoinList(
        limit: Int,
        offset: Int,
        prefix: String,
        symbols: String,
        slugs: String,
        ids: String
    ): Observable<DomainCoinResponse> {
        return fetchAPIInterface
            .getDataFromApi(limit,offset,prefix,symbols,slugs,ids)
            .observeOn(Schedulers.io())
            .map<DomainCoinResponse>{
            when(it){
                is CoinResponseSuccess -> {
                    DomainCoinResponseSuccess(it.data)
                }
                is CoinResponseFailure -> {
                    when(it.error){
                        CoinError.HTTP_EXCEPTION -> DomainCoinResponseFailure(DomainCoinError.HTTP_EXCEPTION, it.e)
                        CoinError.EMPTY_NULL -> DomainCoinResponseFailure(DomainCoinError.EMPTY_NULL)
                        else -> DomainCoinResponseFailure(DomainCoinError.UNKNOWN)
                    }
                }
            }
        }
    }

}