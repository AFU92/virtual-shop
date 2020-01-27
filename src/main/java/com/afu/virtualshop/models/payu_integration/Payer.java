package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Payer.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class Payer {
    private String merchantPayerId;
    private String fullName;
    private String emailAddress;
    private String contactPhone;
    private String dniNumber;
    /**
     * The Billing address.
     */
    Address billingAddress;

}
