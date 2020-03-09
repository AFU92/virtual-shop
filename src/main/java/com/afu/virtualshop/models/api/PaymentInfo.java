package com.afu.virtualshop.models.api;

import lombok.Data;


/**
 * The type Payment info.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data
public class PaymentInfo {
    private String creditCardNumber;
    private String creditCardCVV;
    private String creditCardExpirationDate;
    private String installmentsNumber;
    private PaymentMethod paymentMethod;
    private String printableCreditCardCVV;
    private String token;
}
