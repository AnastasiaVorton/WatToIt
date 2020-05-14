package com.example.wattoit.main.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wattoit.R
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.login.data.RestClient
import com.example.wattoit.main.ui.search.MyItemOnClickListener
import com.example.wattoit.main.ui.search.RecipeAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavFragment : Fragment() {
    private lateinit var favViewModel: FavViewModel
    lateinit var restClient: RestClient
    lateinit var adapter: RecipeAdapter
    lateinit var recipeDatabase: RecipeDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favViewModel =
            ViewModelProviders.of(this).get(FavViewModel::class.java)
        restClient = RestClient()
        recipeDatabase = RecipeDatabase.getInstance(this@FavFragment.requireContext())
        val root = inflater.inflate(R.layout.fragment_fav, container, false).apply {
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
                    val lManager = LinearLayoutManager(this@FavFragment.requireContext())
                    recipiesRView.layoutManager = lManager
                    recipiesRView.adapter = adapter
                }

            }
        }
        val textView: TextView = root.findViewById(R.id.text_home)
        favViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
