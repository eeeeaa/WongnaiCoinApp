package com.tiger.wongnaicoinapp.data.datasource

import com.tiger.wongnaicoinapp.data.model.Coin

sealed class CoinResponse
data class CoinResponseSuccess(val data: List<Coin>?):CoinResponse()
data class CoinResponseFailure(val error: CoinError, val e:Throwable?=null): CoinResponse()
enum class CoinError {
    HTTP_EXCEPTION,
    EMPTY_NULL,
    UNKNOWN
}