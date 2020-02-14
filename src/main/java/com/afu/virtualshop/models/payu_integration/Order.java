package com.afu.virtualshop.models.payu_integration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Order.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data
public class Order {

    private String id;
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
    @JsonIgnore
    private Address address;

    public static Builder createBuilder(){
        return new Builder();
    }

    private Order() {
    }

    private Order(Builder builder) {
        this.id = builder.id;
        this.accountId = builder.accountId;
        this.referenceCode = builder.referenceCode;
        this.description = builder.description;
        this.language = builder.language;
        this.signature = builder.signature;
        this.notifyUrl = builder.notifyUrl;
        this.additionalValues = builder.additionalValues;
        this.buyer = builder.buyer;
        this.address = builder.address;
    }

    public static class Builder {
        private String id;
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

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withReferenceCode(String referenceCode) {
            this.referenceCode = referenceCode;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder withSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder withNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
            return this;
        }

        public Builder withAdditionalValues(Map<AdditionalValueType, AdditionalValue> additionalValues) {
            this.additionalValues = additionalValues;
            return this;
        }

        public Builder addAdditionalValue(AdditionalValueType key, AdditionalValue value){
            if (key != null && value != null) {
                if (additionalValues == null) {
                    additionalValues = new HashMap<>();
                }
                additionalValues.put(key, value);
            }
            return this;
        }

        public Builder withBuyer(Buyer buyer) {
            this.buyer = buyer;
            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Order build(){
            return new Order(this);
        }
    }
}
