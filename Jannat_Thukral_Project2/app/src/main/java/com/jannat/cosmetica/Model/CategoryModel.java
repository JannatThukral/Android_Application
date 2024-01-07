package com.jannat.cosmetica.Model;

public class CategoryModel {
    private String imageUrl;
    private String title;


    public CategoryModel(){};

    public CategoryModel(String url, String title) {
        this.imageUrl = url;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}