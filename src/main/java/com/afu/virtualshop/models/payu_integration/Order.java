package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Order.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class Order {
    private String accountId;
    private String referenceCode;
    private String description;
    private String language;
    private String signature;
    private String notifyUrl;
    /**
     * The Additional values.
     */
    AdditionalValues values;
    /**
     * The Buyer.
     */
    Buyer buyer;
    /**
     * The Shipping address.
     */
    Address address;
}
