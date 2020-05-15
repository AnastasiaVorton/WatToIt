package com.example.wattoit.main.ui.search

import android.os.Bundle
import android.text.util.Linkify
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wattoit.R
import com.example.wattoit.data.RecipeViewModel
import kotlinx.android.synthetic.main.activity_recipe_view.*
import kotlinx.android.synthetic.main.content_recipe_view.*

class RecipeViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_view)
        setSupportActionBar(toolbar)

        val recipe = RecipeViewModel.lastAccessedRecipe

        recipeTitle.text = "TITLE" // TODO

        recipeLink.text = recipe.url
        Linkify.addLinks(recipeLink, Linkify.WEB_URLS)

        fillListView(recipeDietLabels, recipe.dietLabels)

        fillListView(recipeIngredients, recipe.ingredientLines)

        favoriteButton.setOnClickListener {
            TODO("Store current one as liked recipe")
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
