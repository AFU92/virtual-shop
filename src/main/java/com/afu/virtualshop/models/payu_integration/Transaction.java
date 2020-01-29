package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

import java.util.HashMap;
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
    private Map<ExtraParameter, Object> extraParameters;
    private TransactionType type;
    private String paymentMethod;
    private String paymentCountry;
    private String deviceSessionId;
    private String ipAddress;
    private String cookie;
    private String userAgent;
    private String reason;
    private String parentTransactionId;

    private Transaction() {
    }

    private Transaction(Builder builder) {
        this.order = builder.order;
        this.payer = builder.payer;
        this.creditCard = builder.creditCard;
        this.extraParameters = builder.extraParameters;
        this.type = builder.type;
        this.paymentMethod = builder.paymentMethod;
        this.paymentCountry = builder.paymentCountry;
        this.deviceSessionId = builder.deviceSessionId;
        this.ipAddress = builder.ipAddress;
        this.cookie = builder.cookie;
        this.userAgent = builder.userAgent;
        this.reason = builder.reason;
        this.parentTransactionId = builder.parentTransactionId;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {
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
        private Map<ExtraParameter, Object> extraParameters;
        private TransactionType type;
        private String paymentMethod;
        private String paymentCountry;
        private String deviceSessionId;
        private String ipAddress;
        private String cookie;
        private String userAgent;
        private String reason;
        private String parentTransactionId;

        public Builder withOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder withPayer(Payer payer) {
            this.payer = payer;
            return this;
        }

        public Builder withCreditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
            return this;
        }

        public Builder withExtraParameters(Map<ExtraParameter, Object> extraParameters) {
            this.extraParameters = extraParameters;
            return this;
        }

        public Builder addExtraParameter(ExtraParameter key, Object value){
            if (key != null && value != null) {
                if (extraParameters == null) {
                    extraParameters = new HashMap<>();
                }
                extraParameters.put(key, value);
            }
            return this;
        }

        public Builder withType(TransactionType type) {
            this.type = type;
            return this;
        }

        public Builder withPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder withPaymentCountry(String paymentCountry) {
            this.paymentCountry = paymentCountry;
            return this;
        }

        public Builder withDeviceSessionId(String deviceSessionId) {
            this.deviceSessionId = deviceSessionId;
            return this;
        }

        public Builder withIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder withCookie(String cookie) {
            this.cookie = cookie;
            return this;
        }

        public Builder withUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder withParentTransactionId(String parentTransactionId) {
            this.parentTransactionId = parentTransactionId;
            return this;
        }

        public Transaction build(){
            return new Transaction(this);
        }

    }
}
