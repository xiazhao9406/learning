package com.example.foodrecipes.network;

import com.example.foodrecipes.network.response.RecipeDetailsResponse;
import com.example.foodrecipes.network.response.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodRecipeApi {
    @GET("api/v2/recipes")
    Call<SearchResponse> searchRecipe(@Query("q") String keyword, @Query("page") int page);

    @GET("api/v2/recipes/{recipeId}")
    Call<RecipeDetailsResponse> getRecipeDetail(@Path("recipeId") String recipeId);
}
