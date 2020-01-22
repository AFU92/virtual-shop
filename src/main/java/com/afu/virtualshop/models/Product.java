package com.afu.virtualshop.models;

import com.afu.virtualshop.models.hibernate.JsonDataUserType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "product")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
public class Product extends AuditEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @Column(name = "dimentions")
    @Type(type = "JsonDataUserType")
    private Map<String, String> dimentions;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private String image;

    @NotEmpty
    @Column(name = "unit_price", nullable = false)
    private float unitPrice;

    @NotEmpty
    @Column(name = "product_category", nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy="product")
    private List<InputProduct> inputProducts = new ArrayList<>();

    @OneToMany(mappedBy="product")
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Sale> sales;

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

    public Map<String, String> getDimentions() {
        return dimentions;
    }

    public void setDimentions(Map<String, String> dimentions) {
        this.dimentions = dimentions;
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

    public List<SaleProduct> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(List<SaleProduct> saleProducts) {
        this.saleProducts = saleProducts;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}