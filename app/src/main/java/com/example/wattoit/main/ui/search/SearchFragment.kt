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
import com.example.wattoit.data.RecipeViewModel
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.login.data.RestClient
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            searchButton.setOnClickListener {
                search()
            }
            showSavedButton.setOnClickListener {
                toSaved()
            }
        }
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            textSearch.text = it
        })

        restClient = RestClient()
        recipeDatabase = RecipeDatabase.getInstance(this@SearchFragment.requireContext())

        return root
    }

    private fun toSaved() {
        GlobalScope.launch {
            val all = recipeDatabase.recipeDao().getSaved()

            requireActivity().runOnUiThread {
                adapter = RecipeAdapter(
                    all,
                    object :
                        MyItemOnClickListener {
                        override fun onClick(recipe: Recipe) {
                            println("hi babe")
                        }
                    }
                )
                recipesRView.layoutManager =
                    LinearLayoutManager(this@SearchFragment.requireContext())
                recipesRView.adapter = adapter
            }
        }
    }

    private fun search() {
        val query = searchBar.text.toString()
        try {
            RecipeViewModel.search(requireContext(), query) { recipes ->
                adapter = RecipeAdapter(
                    recipes,
                    object : MyItemOnClickListener {
                        override fun onClick(recipe: Recipe) {
                            RecipeViewModel.lastAccessedRecipe = recipe

                            // TODO: do not store recipes here
                            GlobalScope.launch {
                                recipeDatabase.recipeDao().insertRecipe(recipe)
                            }
                        }
                    }
                )
                recipesRView.layoutManager =
                    LinearLayoutManager(this@SearchFragment.requireContext())
                recipesRView.adapter = adapter
            }
        } catch (e: RecipeViewModel.ServiceError) {
            Toast.makeText(
                this@SearchFragment.context,
                e.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
