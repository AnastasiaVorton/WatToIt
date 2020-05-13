package com.example.wattoit.data

import com.example.wattoit.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {

    var authService: AuthService
    var searchService: SearchService

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
    }

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpBuilder.build())
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    init {
        authService = retrofit.create(AuthService::class.java)
        searchService = retrofit.create(SearchService::class.java)
    }
}