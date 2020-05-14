package com.example.wattoit.main.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wattoit.R
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.login.data.RecipeSearchResponse
import com.example.wattoit.login.data.RestClient
import com.example.wattoit.utils.isOkResponseCode
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    lateinit var restClient: RestClient
    lateinit var adapter: RecipeAdapter
    lateinit var recipeDatabase: RecipeDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.activity_search, container, false).apply {
            find_button.setOnClickListener {
                search()
            }
            find_button2.setOnClickListener {
                toSaved()
            }
        }
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            text_search.text = it
        })

        restClient = RestClient()
        recipeDatabase = RecipeDatabase.getInstance(this@SearchFragment.requireContext())

        return root
    }

    private fun toSaved() {
        GlobalScope.launch {
            val all = recipeDatabase.recipeDao().getSaved()
            val clickListener: MyItemOnClickListener = object :
                MyItemOnClickListener {
                override fun onClick(recipe: Recipe) {
                    println("hi babe")
                }
            }

            requireActivity().runOnUiThread {
                adapter = RecipeAdapter(
                    all,
                    clickListener
                )
                val lManager = LinearLayoutManager(this@SearchFragment.requireContext())
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
        restClient.getApiService(requireActivity().applicationContext).findRecipes(token, q)
            .enqueue(
                object : Callback<RecipeSearchResponse> {
                    override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                        println("oh no")
                        Toast.makeText(
                            this@SearchFragment.context,
                            "Error", Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<RecipeSearchResponse>,
                        response: retrofit2.Response<RecipeSearchResponse>
                    ) {
                        println("hey")
                        val clickListener: MyItemOnClickListener = object :
                            MyItemOnClickListener {
                            override fun onClick(recipe: Recipe) {
                                GlobalScope.launch {
                                    recipeDatabase.recipeDao().insertRecipe(recipe)
                                }
                            }
                        }
                        println(response.body().toString())
                        if (isOkResponseCode(response.code())) {
                            val recipes = response.body()?.recipes?.map { e -> e.recipe }
                            adapter = recipes?.let {
                                RecipeAdapter(
                                    it,
                                    clickListener
                                )
                            }!!
                            val lManager = LinearLayoutManager(this@SearchFragment.requireContext())
                            recipiesRView.layoutManager = lManager
                            recipiesRView.adapter = adapter
                            println("OH YEAAAH")
                        }
                    }
                }
            )
    }
}
