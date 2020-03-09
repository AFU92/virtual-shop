package com.afu.virtualshop.models;

import com.afu.virtualshop.models.api.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="credit_card_token")
@Data
public class CreditCardToken extends AuditEntity {
    @Id
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    @NotNull
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Provider provider;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    @JsonIgnore
    private Customer customer;
}
