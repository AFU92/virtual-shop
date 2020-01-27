package com.afu.virtualshop.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Input product.
 *  This entity represents additions to product inventory
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "input_product")
@Data
public class InputProduct extends AuditEntity {

    @Id
    @Column(name = "input_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private String nitProvider;

    private String billNumber;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

}
