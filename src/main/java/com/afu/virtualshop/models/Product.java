package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    private List dimention;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private String image;

    @NotEmpty
    @Column(name = "unit_price", nullable = false)
    private float unitPrice;

    @OneToMany(mappedBy="input_product")
    private List<InputProduct> inputProducts = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List getDimention() {
        return dimention;
    }

    public void setDimention(List dimention) {
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<InputProduct> getInputProducts() {
        return inputProducts;
    }

    public void setInputProducts(List<InputProduct> inputProducts) {
        this.inputProducts = inputProducts;
    }
}