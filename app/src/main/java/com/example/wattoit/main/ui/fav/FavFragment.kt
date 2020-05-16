package com.example.wattoit.main.ui.fav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wattoit.R
import com.example.wattoit.data.RecipeViewModel
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.main.ui.search.MyItemOnClickListener
import com.example.wattoit.main.ui.search.RecipeAdapter
import com.example.wattoit.recipe.RecipeViewActivity
import kotlinx.android.synthetic.main.fragment_fav.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FavFragment : Fragment() {


    private val recipeViewModel: RecipeViewModel by inject()
    lateinit var adapter: RecipeAdapter
    lateinit var recipeDatabase: RecipeDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recipeDatabase = RecipeDatabase.getInstance(this@FavFragment.requireContext())

        return inflater.inflate(R.layout.fragment_fav, container, false).apply {
            GlobalScope.launch {
                val all = recipeDatabase.recipeDao().getSaved()
                val clickListener: MyItemOnClickListener = object :
                    MyItemOnClickListener {
                    override fun onClick(recipe: Recipe) {
                        println("hi babe")
                        recipeViewModel.lastAccessedRecipe = recipe
                        chooseRecipe(recipe)
                    }
                }

                requireActivity().runOnUiThread {
                    adapter = RecipeAdapter(
                        all,
                        clickListener
                    )
                    val lManager = LinearLayoutManager(requireContext())
                    favRView.layoutManager = lManager
                    favRView.adapter = adapter
                }

            }

        }

    }

    private fun chooseRecipe(recipe: Recipe) {
        val intent = Intent(activity, RecipeViewActivity::class.java).apply {}
        activity?.startActivity(intent)
        // GlobalScope.launch { recipeDatabase.recipeDao().insertRecipe(recipe) }
    }
}
