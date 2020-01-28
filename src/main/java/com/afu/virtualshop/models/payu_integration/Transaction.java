package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

import java.util.Map;

/**
 * The type Transaction.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class Transaction {

    /**
     * The Order.
     */
    Order order;
    /**
     * The Payer.
     */
    Payer payer;
    /**
     * The Credit card.
     */
    CreditCard creditCard;
    /**
     * The Extra parameters.
     */
    private Map <ExtraParameter, Object> extraParameters;
    private TransactionType type;
    private String paymentMethod;
    private String paymentCountry;
    private String deviceSessionId;
    private String ipAddress;
    private String cookie;
    private String userAgent;
}
