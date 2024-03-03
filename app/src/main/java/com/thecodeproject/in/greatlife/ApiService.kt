package com.thecodeproject.`in`.greatlife

import com.thecodeproject.`in`.greatlife.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/findByNutrients")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("maxProtein") maxProtein: Int,
        @Query("maxFat") maxFat: Int,
        @Query("number") number: Int
    ): List<Recipe>
}
