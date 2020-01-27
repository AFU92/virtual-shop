package com.afu.virtualshop.models;

import com.afu.virtualshop.models.hibernate.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Product.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Data
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

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private String image;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private float unitPrice;

    @NotNull
    @Column(name = "product_category", nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy="product")
    private List<InputProduct> inputProducts = new ArrayList<>();

    @OneToMany(mappedBy="product")
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Sale> sales;

    }