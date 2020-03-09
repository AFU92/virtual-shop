package com.afu.virtualshop.models.api;

import com.afu.virtualshop.models.Customer;
import lombok.Data;

@Data
public class CreditCardTokenRequest {
    private Customer customer;
    private PaymentInfo paymentInfo;
}
