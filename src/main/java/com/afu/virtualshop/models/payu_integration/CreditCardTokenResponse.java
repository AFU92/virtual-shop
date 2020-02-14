package com.afu.virtualshop.models.payu_integration;

import com.afu.virtualshop.models.api.PaymentMethod;
import lombok.Data;

@Data
public class CreditCardTokenResponse {
    private String creditCardTokenId;
    private String payerId;
    private String name;
    private String identificationNumber;
    private PaymentMethod paymentMethod;
    private String number;
    private String expirationDate;
    private String creationDate;
    private String maskedNumber;
    private String errorDescription;
}
