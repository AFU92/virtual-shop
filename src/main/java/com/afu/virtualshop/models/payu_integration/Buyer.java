package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Buyer.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class Buyer {
    private String merchantBuyerId;
    private String fullName;
    private String emailAddress;
    private String contactPhone;
    private String dniNumber;
    /**
     * The Shipping address.
     */
    Address shippingAddress;

}
