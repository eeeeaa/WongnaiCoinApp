package com.tiger.wongnaicoinapp.presentation.interactor

import com.tiger.wongnaicoinapp.domain.contract.GetCoinListInterface
import com.tiger.wongnaicoinapp.domain.entity.DomainCoinError
import com.tiger.wongnaicoinapp.domain.entity.DomainCoinResponseFailure
import com.tiger.wongnaicoinapp.domain.entity.DomainCoinResponseSuccess
import com.tiger.wongnaicoinapp.presentation.contract.CoinAPIInteractorInterface
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CoinAPIInteractor(private val getCoinListInterface: GetCoinListInterface):CoinAPIInteractorInterface {
    override fun getCoinList(
        limit: Int?,
        offset: Int?,
        prefix: String?,
        symbols: String?,
        slugs: String?,
        ids: String?
    ): Observable<PresCoinResponse> {
        return getCoinListInterface.getCoinList(limit, offset, prefix, symbols, slugs, ids)
            .observeOn(Schedulers.io())
            .map<PresCoinResponse> {
                when (it) {
                    is DomainCoinResponseSuccess -> {
                        PresCoinResponseSuccess(it.data)
                    }
                    is DomainCoinResponseFailure -> {
                        when (it.error) {
                            DomainCoinError.HTTP_EXCEPTION -> PresCoinResponseFailure(
                                PresCoinError.HTTP_EXCEPTION,
                                it.e
                            )
                            DomainCoinError.EMPTY_NULL -> PresCoinResponseFailure(PresCoinError.EMPTY_NULL)
                            else -> PresCoinResponseFailure(PresCoinError.UNKNOWN)
                        }
                    }
                }
            }
    }
}