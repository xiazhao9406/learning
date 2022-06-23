package com.example.foodrecipes.requests;

import static com.example.foodrecipes.util.Constants.NETWORK_TIMEOUT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes.AppExecutors;
import com.example.foodrecipes.model.Recipe;
import com.example.foodrecipes.network.response.RecipeDetailsResponse;
import com.example.foodrecipes.util.Constants;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class RecipeApiClient {
    private static RecipeApiClient instance;

    private MutableLiveData<List<Recipe>> mRecipes;

    private static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipesApi() {
        final Future handler = AppExecutors.getInstance().networkIO().submit();

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MICROSECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequests;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequests = false;
        }

        @Override
        public void run() {
            if (cancelRequests) {
                return;
            }
        }

        private Call<RecipeDetailsResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    Constants.BASE_URL,
                    query,
                    String.valueOf(pageNumber)
            );
        }
    }


}
