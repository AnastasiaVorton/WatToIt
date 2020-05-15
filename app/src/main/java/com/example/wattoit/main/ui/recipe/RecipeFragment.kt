package com.example.wattoit.main.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wattoit.R
import com.example.wattoit.data.RecipeViewModel
import org.koin.android.ext.android.inject

class RecipeFragment : Fragment() {
//    private var recipeViewModel: RecipeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_recipe, container, false)
        val textView: TextView = root.findViewById(R.id.text_recipe)
        return root
    }
}
