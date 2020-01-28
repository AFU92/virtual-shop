package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type PayU Request .
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */

@Data

public class PayuRequest {

        private String language;
        private Command command;
        /**
         * The Merchant.
         */
        Merchant merchant;
        /**
         * The Transaction.
         */
        Transaction transaction;
        private boolean test;

}
