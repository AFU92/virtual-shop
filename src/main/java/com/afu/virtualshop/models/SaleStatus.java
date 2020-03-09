package com.afu.virtualshop.models;

/**
 * The enum Sale status.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public enum SaleStatus {

    /**
     * Created sale status.
     */
    CREATED,
    /**
     * Payment pending sale status.
     */
    PAYMENT_PENDING,
    /**
     * Payment approved sale status.
     */
    PAYMENT_APPROVED,
    /**
     * Payment rejected sale status.
     */
    PAYMENT_REJECTED,
    /**
     * Canceled sale status.
     */
    CANCELED,
    /**
     * Shipped sale status.
     */
    SHIPPED,
    /**
     * Refund rejected status.
     */
    REFUND_REJECTED,
    /**
     * Refunded sale status.
     */
    REFUNDED;
}
