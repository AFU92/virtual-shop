package com.afu.virtualshop.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Customer.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Data

public class Customer extends AuditEntity {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @NotEmpty
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotEmpty
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy="customer")
    private List<Sale> sales = new ArrayList<>();

}