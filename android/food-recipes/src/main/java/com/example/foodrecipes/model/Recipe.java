package com.example.foodrecipes.model;

import java.util.Arrays;

public class Recipe {
    private String imageUrl;
    private float socialUrl;
    private String publisher;
    private String sourceUrl;
    private String id;
    private String title;
    private String[] ingredients;

    public Recipe() {
    }

    public Recipe(String imageUrl, float socialUrl, String publisher, String sourceUrl, String id, String title, String[] ingredients) {
        this.imageUrl = imageUrl;
        this.socialUrl = socialUrl;
        this.publisher = publisher;
        this.sourceUrl = sourceUrl;
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getSocialUrl() {
        return socialUrl;
    }

    public void setSocialUrl(float socialUrl) {
        this.socialUrl = socialUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "imageUrl='" + imageUrl + '\'' +
                ", socialUrl=" + socialUrl +
                ", publisher='" + publisher + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
