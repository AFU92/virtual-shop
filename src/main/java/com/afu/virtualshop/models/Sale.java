package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

public class Sale {

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @NotEmpty
    @Column(name = "refund_percent", nullable = false)
    private Integer refundPercent;

    @NotEmpty
    @Column(name = "refund_value", nullable = false)
    private String refundValue;

    @ManyToOne
    @JoinColumn(name="real_sale", nullable=false)
    private Product realSale;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;
}
