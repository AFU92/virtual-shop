package com.afu.virtualshop.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sale.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Data

public class Sale extends AuditEntity {

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @Column(name = "refund_percent", nullable = false)
    private Integer refundPercent;

    @Column(name = "refund_value", nullable = false)
    private String refundValue;

    @Column(nullable = false)
    private SaleStatus status;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy="sale")
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @OneToMany(mappedBy="sale")
    private List<SaleRefundedProduct> saleRefundedProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
