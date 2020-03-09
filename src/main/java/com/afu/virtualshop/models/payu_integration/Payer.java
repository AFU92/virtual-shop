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

    private Payer() {
    }

    private Payer(Builder builder) {
        this.merchantPayerId = builder.merchantPayerId;
        this.fullName = builder.fullName;
        this.emailAddress = builder.emailAddress;
        this.contactPhone = builder.contactPhone;
        this.dniNumber = builder.dniNumber;
        this.billingAddress = builder.billingAddress;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String merchantPayerId;
        private String fullName;
        private String emailAddress;
        private String contactPhone;
        private String dniNumber;
        /**
         * The Billing address.
         */
        Address billingAddress;

        public Builder withMerchantPayerId(String merchantPayerId) {
            this.merchantPayerId = merchantPayerId;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder withContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
            return this;
        }

        public Builder withDniNumber(String dniNumber) {
            this.dniNumber = dniNumber;
            return this;
        }

        public Builder withBillingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Payer build() {
            return new Payer(this);
        }
    }
}
