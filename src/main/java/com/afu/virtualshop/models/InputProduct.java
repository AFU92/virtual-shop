package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InputProduct implements Serializable {

    @Id
    @Column(name = "input_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private String nitProvider;

    private String billNumber;

    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @OneToMany(mappedBy="sale_product_id")
    private List<InputProduct> saleProduct = new ArrayList<>();

}
