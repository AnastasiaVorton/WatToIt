package com.example.wattoit.data

import com.example.wattoit.login.data.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    @GET("search")
    fun findRecipes(
        @Query("q") q: String
    ): Call<RecipeSearchResponse>
}
