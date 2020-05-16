package com.example.wattoit.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.utils.isOkResponseCode
import com.github.ajalt.timberkt.Timber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel (private val client: RestClient): ViewModel() {
    class ServiceError(message: String) : RuntimeException(message)


    private var recipesMld = MutableLiveData<List<Recipe>>()

    val recipes
        get() = recipesMld.value ?:throw ServiceError("No recipe list set")

    fun search(context: Context, query: String, callback: (List<Recipe>) -> Unit) {
        client.getSearchService(context).findRecipes(query)
            .enqueue(object : Callback<RecipeSearchResponse> {
                override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                    Timber.d{"Error while getting recipes"}
                    throw ServiceError("Search request failed")
                }

                override fun onResponse(
                    call: Call<RecipeSearchResponse>,
                    response: Response<RecipeSearchResponse>
                ) {
                    if (isOkResponseCode(response.code())) {
                        if (response.body() == null) {
                            Timber.d{"Response has no body"}
                            throw ServiceError("Response has no body")
                        } else {
                            if (response.body()!!.recipes == null) {
                                Timber.d{"No recipes found"}
                                throw ServiceError("No recipes found")
                            }
                            recipesMld.value = response.body()!!.recipes?.map { e -> e.recipe }
                            callback(recipes)
                        }
                    } else {
                        Timber.d{"Error while getting recipes: ${response.message()}"}
                        throw ServiceError("Search request failed with code ${response.code()}")
                    }
                }
            })
    }

    private var lastAccessedRecipeMld = MutableLiveData<Recipe>()

    var lastAccessedRecipe
        set(value) {
            lastAccessedRecipeMld.value = value
        }
        get() = lastAccessedRecipeMld.value ?: throw ServiceError("No last accessed recipe set")
}
