package com.tiger.wongnaicoinapp.presentation.provider

import android.util.Log
import com.tiger.wongnaicoinapp.data.datasource.CoinService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.Request


class NetworkProvider(private val url:String,private val token:String) {
    fun provideRetrofit(): Retrofit {
        Log.d("Final Url", url)
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("x-access-token", token)
                .build()
            chain.proceed(newRequest)
        }
        return builder.client(client.build()).build()
    }


    fun provideCoinAPIClient(): CoinService {
        return provideRetrofit().create(CoinService::class.java)
    }
}