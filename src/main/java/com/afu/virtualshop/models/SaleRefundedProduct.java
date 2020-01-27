package com.afu.virtualshop.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Sale refunded product.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "sale_refunded_product")
@Data

public class SaleRefundedProduct extends AuditEntity {

    @Id
    @Column(name = "sale_refunded_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotEmpty
    @Column(name = "total_value", nullable = false)
    private String totalPrice;

    @OneToOne
    @JoinColumn(name="sale_product_id", nullable=false)
    private SaleProduct saleProduct;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;

}
