package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Shipping address.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;

}