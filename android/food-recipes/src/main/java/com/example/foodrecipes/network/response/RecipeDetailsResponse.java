package com.example.foodrecipes.network.response;

import com.example.foodrecipes.model.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeDetailsResponse {
    @SerializedName("recipe")
    @Expose
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeDetailsResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
