package com.example.foodrecipes.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static FoodRecipeApi getFoodRecipeApi() {
        return retrofit.create(FoodRecipeApi.class);
    }
}
