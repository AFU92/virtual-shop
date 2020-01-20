package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

public class SaleProduct {

    @Id
    @Column(name = "sale_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "unit_price", nullable = false)
    private String unitPrice;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotEmpty
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;

}
