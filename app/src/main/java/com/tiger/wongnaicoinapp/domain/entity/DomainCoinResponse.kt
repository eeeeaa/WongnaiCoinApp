package com.tiger.wongnaicoinapp.domain.entity

import com.tiger.wongnaicoinapp.data.model.Coin

sealed class DomainCoinResponse
data class DomainCoinResponseSuccess(val data: List<Coin>?):DomainCoinResponse()
data class DomainCoinResponseFailure(val error: DomainCoinError, val e:Throwable?=null): DomainCoinResponse()
enum class DomainCoinError {
    HTTP_EXCEPTION,
    EMPTY_NULL,
    UNKNOWN
}