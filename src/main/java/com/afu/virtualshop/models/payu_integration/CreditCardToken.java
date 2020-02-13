package com.afu.virtualshop.models.payu_integration;

import lombok.Builder;
import lombok.Data;

/**
 * The type PayU Credit Card Token.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data
@Builder
public class CreditCardToken {
    private String payerId;
    private String name;
    private String identificationNumber;
    private String paymentMethod;
    private String number;
    private String expirationDate;
}
