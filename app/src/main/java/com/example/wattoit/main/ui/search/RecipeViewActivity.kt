package com.example.wattoit.main.ui.search

import android.os.Bundle
import android.text.util.Linkify
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wattoit.R
import com.example.wattoit.data.RecipeViewModel
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.utils.downloadImage
import kotlinx.android.synthetic.main.activity_recipe_view.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_recipe_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RecipeViewActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeViewModel by inject()
    private val db: RecipeDatabase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_view)
        setSupportActionBar(toolbar)

        val recipe = recipeViewModel.lastAccessedRecipe

        recipeTitle.text = recipe.label//"TITLE" // TODO
        imageFav.downloadImage(recipe.image)
        recipeLink.text = recipe.url
        Linkify.addLinks(recipeLink, Linkify.WEB_URLS)

        fillListView(recipeDietLabels, recipe.dietLabels)

        fillListView(recipeIngredients, recipe.ingredientLines)

        favoriteButton.setOnClickListener {
            GlobalScope.launch {
                db.recipeDao().insertRecipe(recipe)
            }
        }
    }

    private fun fillListView(view: LinearLayout, dietLabels: List<String>) {
        if (dietLabels.isNotEmpty()) {
            view.removeAllViews()
            for (label in dietLabels) {
                val tv = TextView(applicationContext).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = label
                }
                view.addView(tv)
            }
        }
    }
}
