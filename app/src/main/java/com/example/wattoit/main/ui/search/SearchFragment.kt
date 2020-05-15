package com.example.wattoit.main.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wattoit.R
import com.example.wattoit.data.RecipeViewModel
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.recipe.RecipeViewActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private val recipeViewModel: RecipeViewModel by inject()
    lateinit var adapter: RecipeAdapter
    private val recipeDatabase: RecipeDatabase by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_search, container, false).apply {
            searchButton.setOnClickListener {
                search()
            }
        }

        return root
    }

    private fun chooseRecipe(recipe: Recipe) {
        val intent = Intent(activity, RecipeViewActivity::class.java).apply {}
        activity?.startActivity(intent)
        // GlobalScope.launch { recipeDatabase.recipeDao().insertRecipe(recipe) }
    }

    private fun search() {
        val query = searchBar.text.toString()
        try {
            recipeViewModel.search(requireContext(), query) { recipes ->
                adapter = RecipeAdapter(
                    recipes,
                    object : MyItemOnClickListener {
                        override fun onClick(recipe: Recipe) {
                            recipeViewModel.lastAccessedRecipe = recipe
                            chooseRecipe(recipe)
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
