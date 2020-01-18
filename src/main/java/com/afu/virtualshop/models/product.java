package com.afu.virtualshop.models;

import java.util.ArrayList;

public class product {

    private String name;
    private String description;
    private ArrayList dimention;
    private Integer quantity;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList getDimention() {
        return dimention;
    }

    public void setDimention(ArrayList dimention) {
        this.dimention = dimention;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

