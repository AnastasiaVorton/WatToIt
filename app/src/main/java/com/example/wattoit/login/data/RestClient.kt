package com.example.wattoit.login.data

import android.content.Context
import com.example.wattoit.BuildConfig
import com.example.wattoit.data.AuthInterceptor
import com.example.wattoit.data.RecipeService
import com.example.wattoit.data.SearchService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    private lateinit var apiService: RecipeService
    private lateinit var searchService: SearchService

    fun getApiService(context: Context): RecipeService {
        if (!::apiService.isInitialized) {
            apiService = buildRetrofit(context).create(RecipeService::class.java)
        }
        return apiService
    }

    fun getSearchService(context: Context): SearchService {
        if (!::searchService.isInitialized) {
            searchService = buildRetrofit(context).create(SearchService::class.java)
        }
        return searchService
    }

    private fun buildRetrofit(context: Context): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        client(buildOkHttpClient(context))
    }.build()

    private fun buildOkHttpClient(context: Context): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        addInterceptor(AuthInterceptor(context))
    }.build()
}
