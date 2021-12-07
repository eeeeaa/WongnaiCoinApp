package com.tiger.wongnaicoinapp.presentation.interactor

import com.tiger.wongnaicoinapp.data.model.Coin

sealed class PresCoinResponse
data class PresCoinResponseSuccess(val data: List<Coin>):PresCoinResponse()
data class PresCoinResponseFailure(val error: PresCoinError, val e:Throwable?=null): PresCoinResponse()
enum class PresCoinError {
    HTTP_EXCEPTION,
    EMPTY_NULL,
    UNKNOWN
}