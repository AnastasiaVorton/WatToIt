package com.example.wattoit

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wattoit.domain.entity.Recipe
import com.example.wattoit.utils.downloadImage
import com.example.wattoit.utils.inflate

class RecipeAdapter(private val items: List<Recipe>, private val clickListener: MyItemOnClickListener) : RecyclerView.Adapter<RecipeAdapter.RecipeItemViewHolder>(){
    inner class RecipeItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView = itemView.findViewById<ImageView>(R.id.imageView);
        private val name: TextView = itemView.findViewById(R.id.name)
        private val description: TextView = itemView.findViewById(R.id.description)
        init {
            itemView.setOnClickListener { clickListener.onClick(items[layoutPosition]) }
        }
        fun bind(recipe: Recipe) {
            imageView.downloadImage(recipe.image)
            name.text = recipe.label
            description.text = recipe.dietLabels.joinToString {it->"\'${it}\'"} + ", " +recipe.healthLabels.joinToString {it->"\'${it}\'"}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val inflatedView = parent.inflate(R.layout.recipe_short_description, false)
        return RecipeItemViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
interface MyItemOnClickListener {
    fun onClick(recipe: Recipe)
}