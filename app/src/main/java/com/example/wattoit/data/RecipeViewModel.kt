package com.example.wattoit.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.login.data.RecipeSearchResponse
import com.example.wattoit.login.data.RestClient
import com.example.wattoit.utils.isOkResponseCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeViewModel : ViewModel() {
    class ServiceError(message: String) : RuntimeException(message)

    private val client = RestClient()

    private var recipesMld = MutableLiveData<List<Recipe>>()

    val recipes
        get() = recipesMld.value ?: throw ServiceError("No recipe list set")

    fun search(context: Context, query: String, callback: (List<Recipe>) -> Unit) {
        client.getSearchService(context).findRecipes(query)
            .enqueue(object : Callback<RecipeSearchResponse> {
                override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                    throw ServiceError("Search request failed")
                }

                override fun onResponse(
                    call: Call<RecipeSearchResponse>,
                    response: Response<RecipeSearchResponse>
                ) {
                    if (isOkResponseCode(response.code())) {
                        if (response.body() == null) {
                            throw ServiceError("Response has no body")
                        } else {
                            recipesMld.value = response.body()!!.recipes.map { e -> e.recipe }
                            callback(recipes)
                        }
                    } else {
                        throw ServiceError("Search request failed with code ${response.code()}")
                    }
                }
            })
    }

    private var lastAccessedRecipeMld = MutableLiveData<Recipe>()

    val lastAccessedRecipe
        get() = lastAccessedRecipeMld.value ?: throw ServiceError("No last accessed recipe set")
}
