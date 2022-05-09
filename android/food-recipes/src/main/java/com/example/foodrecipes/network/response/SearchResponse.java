package com.example.foodrecipes.network.response;

import com.example.foodrecipes.model.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class SearchResponse {
    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("recipes")
    @Expose
    private Recipe[] recipes;

    public int getCount() {
        return count;
    }

    public Recipe[] getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "count=" + count +
                ", recipes=" + Arrays.toString(recipes) +
                '}';
    }
}
