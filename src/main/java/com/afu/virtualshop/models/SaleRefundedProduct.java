package com.afu.virtualshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "total_value", nullable = false)
    private Float totalPrice;

    @OneToOne
    @JoinColumn(name="sale_product_id", nullable=false)
    private SaleProduct saleProduct;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    @JsonIgnore
    private Sale sale;

}
