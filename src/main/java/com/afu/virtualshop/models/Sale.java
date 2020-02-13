package com.afu.virtualshop.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "refund_percent")
    private Float refundPercent;

    @Column(name = "refund_value")
    private Float refundValue;

    @Column(name = "refund_reason")
    private String refundReason;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "external_sale_id")
    private String externalSaleId;

    @OneToMany(mappedBy="sale")
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @OneToMany(mappedBy="sale")
    private List<SaleRefundedProduct> saleRefundedProducts = new ArrayList<>();

    @OneToMany(mappedBy="sale")
    private List<ProviderTransaction> providerTransactions = new ArrayList<>();

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
