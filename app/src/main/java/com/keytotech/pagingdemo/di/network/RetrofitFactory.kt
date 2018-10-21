package com.keytotech.pagingdemo.di.network

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitFactory
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
object RetrofitFactory {

    private const val KEEP_ALIVE_DURATION = (35 * 1000).toLong()
    private const val MAX_IDLE_CONNECTIONS = 3

    fun createApiRetrofit(
        baseUrl: String,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient(loggingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectionPool(
                ConnectionPool(
                    MAX_IDLE_CONNECTIONS,
                    KEEP_ALIVE_DURATION,
                    TimeUnit.MILLISECONDS
                )
            )
            .build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}