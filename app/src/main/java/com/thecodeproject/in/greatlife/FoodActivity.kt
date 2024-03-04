package com.thecodeproject.`in`.greatlife

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thecodeproject.`in`.greatlife.adapter.RecipeAdapter
import com.thecodeproject.`in`.greatlife.databinding.ActivityFoodBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecipeAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnSearch.setOnClickListener { createRecipe() }

    }

    private fun createRecipe() {
        binding.etNumber.visibility = View.GONE
        binding.etMaxProtein.visibility = View.GONE
        binding.maxFat.visibility = View.GONE
        binding.btnSearch.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        // Make API request
        val apiKey = "8f518a8cbc29432fbd11c4700231119f"
        val maxProtein = binding.etMaxProtein.text.toString().toInt()
        val maxFat = binding.maxFat.text.toString().toInt()
        val number = binding.etNumber.text.toString().toInt()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)


        GlobalScope.launch {
            val recipes = apiService.getRecipes(apiKey, maxProtein, maxFat, number)
            withContext(Dispatchers.Main) {
                // Update UI with the fetched recipes
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.setRecipes(recipes)
            }
        }
    }

}