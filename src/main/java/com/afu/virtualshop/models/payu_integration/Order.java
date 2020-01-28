package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

import java.util.Map;

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
    private Map<AdditionalValueType, AdditionalValue> additionalValues;
    /**
     * The Buyer.
     */
    private Buyer buyer;
    /**
     * The Shipping address.
     */
    private Address address;
}
