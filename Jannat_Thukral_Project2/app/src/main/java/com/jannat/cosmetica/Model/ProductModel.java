package com.jannat.cosmetica.Model;

import java.io.Serializable;
import java.util.HashMap;

public class ProductModel implements Serializable {
    private String description;
    private String price;
    private String size;
    private String title;
    private String visual;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisual() {
        return visual;
    }

    public void setVisual(String visual) {
        this.visual = visual;
    }

    public ProductModel(){};

    public ProductModel(String size, String description, String price, String title, String visual){
        this.size = size;
        this.description = description;
        this.price = price;
        this.title = title;
        this.visual = visual;
    }

}
