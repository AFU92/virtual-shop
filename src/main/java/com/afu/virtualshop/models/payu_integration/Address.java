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

    private Address() {
    }

    private Address(Builder builder) {
        this.street1 = builder.street1;
        this.street2 = builder.street2;
        this.city = builder.city;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.phone = builder.phone;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String street1;
        private String street2;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private String phone;

        public Builder withStreet1(String street1) {
            this.street1 = street1;
            return this;
        }

        public Builder withStreet2(String street2) {
            this.street2 = street2;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}