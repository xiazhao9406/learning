package com.example.foodrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodrecipes.model.Recipe;
import com.example.foodrecipes.network.RetrofitHelper;
import com.example.foodrecipes.network.response.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SearchResponse> searchResponse = RetrofitHelper.getFoodRecipeApi().searchRecipe("chinese", 1);
                searchResponse.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.code() == 200) {
                            int count = response.body().getCount();
                            Recipe[] recipes = response.body().getRecipes();
                            Log.d(TAG, "got " + count + " recipes");
                            for (Recipe recipe : recipes) {
                                Log.d(TAG, recipe.getTitle());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}