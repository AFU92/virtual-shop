package com.afu.virtualshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Sale product.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "sale_product")
@Data
public class SaleProduct extends AuditEntity {

    @Id
    @Column(name = "sale_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    @JsonIgnore
    private Sale sale;

    @OneToOne(mappedBy = "saleProduct")
    private SaleRefundedProduct saleRefundedProduct;
}
