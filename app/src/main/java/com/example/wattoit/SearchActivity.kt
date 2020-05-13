package com.example.wattoit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wattoit.data.RecipeSearchResponse
import com.example.wattoit.data.RestClient
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SearchActivity : AppCompatActivity() {

    lateinit var restClient: RestClient
    lateinit var adapter: RecipeAdapter
    lateinit var recipeDatabase: RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        restClient = RestClient()
        recipeDatabase = RecipeDatabase.getInstance(this@SearchActivity)
        find_button.setOnClickListener {
            search()
        }

        find_button2.setOnClickListener{
            toSaved()
        }
    }

    private fun toSaved(){
        GlobalScope.launch {
            val all = recipeDatabase.recipeDao().getSaved()
            val clickListener: MyItemOnClickListener = object : MyItemOnClickListener {
                override fun onClick(recipe: Recipe) {
                    println("hi babe")
                }
            }

            runOnUiThread {
                adapter = RecipeAdapter(all, clickListener)
                val lManager = LinearLayoutManager(this@SearchActivity)
                recipiesRView.layoutManager = lManager
                recipiesRView.adapter = adapter
            }

        }
    }


    private fun search() {
        val q = search_bar.text.toString()
        println(q)
        val token =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyZXN1bHQiOnsiaWQiOiI4YjdjNjU4ZS0wYjkwLTQ3MWMtOTI4MC0zN2FmNDlkNDM4ZGMiLCJ1c2VybmFtZSI6ImJha2xhbiIsImVtYWlsIjoic29iYWthQHNvYmFrYS5wZXMifSwiaWF0IjoxNTg5MzgwMTk4LCJleHAiOjE1ODk0MTYxOTh9.Pr4aTiaisEbKVS3tmcWxLPUPxqowiTMDuRlXbcc6Ig4"
        restClient.searchService.findRecipes(token, q).enqueue(
            object : Callback<RecipeSearchResponse> {
                override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                    println("oh no")
                    Toast.makeText(
                        this@SearchActivity,
                        "Error", Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<RecipeSearchResponse>,
                    response: retrofit2.Response<RecipeSearchResponse>
                ) {
                    println("hey")
                    val clickListener: MyItemOnClickListener = object : MyItemOnClickListener {
                        override fun onClick(recipe: Recipe) {
                            GlobalScope.launch {
                                recipeDatabase.recipeDao().insertRecipe(recipe)
                            }
                        }
                    }
                    if (response.code() == 200) {
                        val recipes = response.body()?.recipes?.map { e -> e.recipe }
                        adapter = recipes?.let { RecipeAdapter(it, clickListener) }!!
                        val lManager = LinearLayoutManager(this@SearchActivity)
                        recipiesRView.layoutManager = lManager
                        recipiesRView.adapter = adapter
                        println("OH YEAAAH")
                    }
                }
            }
        )

    }

}
