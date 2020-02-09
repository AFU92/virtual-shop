package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Credit card.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class CreditCard {
    private String number;
    private String securityCode;
    private String expirationDate;
    private String name;

    private CreditCard() {
    }

    private CreditCard(Builder builder) {
        this.number = builder.number;
        this.securityCode = builder.securityCode;
        this.expirationDate = builder.expirationDate;
        this.name = builder.name;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String number;
        private String securityCode;
        private String expirationDate;
        private String name;

        public Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withSecurityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public Builder withExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(this);
        }
    }

}
