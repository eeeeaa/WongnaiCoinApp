package com.tiger.wongnaicoinapp.data.datasource

import com.tiger.wongnaicoinapp.data.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinService {
    @GET("public/coins")
    fun getCoin(@Query("limit") limit:Int?,
                @Query("offset")offset:Int?,
                @Query("prefix")prefix:String?,
                @Query("symbols")symbols:String?,
                @Query("slugs")slugs:String?,
                @Query("ids")ids:String?):Observable<Response>
}