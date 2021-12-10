package com.tiger.wongnaicoinapp.data.datasource
import android.util.Log
import com.tiger.wongnaicoinapp.BuildConfig
import com.tiger.wongnaicoinapp.data.contract.FetchAPIInterface
import com.tiger.wongnaicoinapp.data.model.Coin
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.SocketTimeoutException


class FetchAPI(private val coinService: CoinService):FetchAPIInterface {
    /*
    * NOTE:
    * - API v1 deprecated 31st december (need to use v1 for some missing parameter Ex. description,etc)
    *
    * */
    val TAG = "Fetch API"
    override fun getDataFromApi(
        limit: Int?,
        offset: Int?,
        prefix: String?,
        symbols: String?,
        slugs: String?,
        ids: String?
    ): Observable<CoinResponse> {
        return coinService.getCoin(limit,offset,prefix,symbols,slugs,ids)
            .retry(5)
            .observeOn(Schedulers.io())
            .map<CoinResponse> { res ->
                Log.d("Fetch API success", res.toString())
                val coins: List<Coin>? = res.data?.coins
                val emptylist:MutableList<Coin?> = ArrayList()
                emptylist.add(null)
                if(coins.isNullOrEmpty()){
                    if(BuildConfig.DEBUG) Log.d(TAG, "getDataFromApi--Null: $coins")
                    CoinResponseFailure(CoinError.EMPTY_NULL)
                }else{
                    if(BuildConfig.DEBUG) Log.d(TAG, "getDataFromApi--NonNull: $coins")
                    if(coins == emptylist){
                        CoinResponseFailure(CoinError.EMPTY_NULL)
                    }else {
                        CoinResponseSuccess(coins)
                    }
                }

            }.onErrorReturn {
                    throwable ->
                throwable.message?.let { Log.d("Fetch API fail", it) }
                when(throwable){
                    is HttpException -> CoinResponseFailure(CoinError.HTTP_EXCEPTION,throwable)
                    is SocketTimeoutException -> CoinResponseFailure(CoinError.HTTP_EXCEPTION,throwable)
                    else -> CoinResponseFailure(CoinError.UNKNOWN,throwable)
                }
            }
    }

}