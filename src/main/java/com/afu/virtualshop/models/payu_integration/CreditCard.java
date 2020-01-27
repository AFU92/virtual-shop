package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Credit card.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class CreditCard {
    private String number;
    private String securityCode;
    private String expirationDate;
    private String name;

}
