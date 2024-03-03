package com.thecodeproject.`in`.greatlife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thecodeproject.`in`.greatlife.R
import com.thecodeproject.`in`.greatlife.model.Recipe

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = emptyList()

    fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val proteinTextView: TextView = itemView.findViewById(R.id.proteinTextView)
        private val fatTextView: TextView = itemView.findViewById(R.id.fatTextView)
        private val carbsTextView: TextView = itemView.findViewById(R.id.carbsTextView)

        fun bind(recipe: Recipe) {
            titleTextView.text = recipe.title
            proteinTextView.text = "Protein: ${recipe.protein}"
            fatTextView.text = "Fat: ${recipe.fat}"
            carbsTextView.text = "Carbs: ${recipe.carbs}"

            Glide.with(itemView)
                .load(recipe.image)
                .into(imageView)
        }
    }
}
