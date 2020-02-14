package com.afu.virtualshop.models.payu_integration;

import com.afu.virtualshop.services.external_providers.payments.impl.CreditCardTokenInformation;
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

        /**
         * The Credit Card Token
         */
        CreditCardToken creditCardToken;

        /**
         * The Credit Card Token Information
         */
        CreditCardTokenInformation creditCardTokenInformation;

        private boolean test;

        private PayuRequest(){}

        private PayuRequest(Builder builder){
                this.language = builder.language;
                this.command = builder.command;
                this.merchant = builder.merchant;
                this.transaction = builder.transaction;
                this.creditCardToken = builder.creditCardToken;
                this.creditCardTokenInformation = builder.creditCardTokenInformation;
                this.test = builder.test;
        }

        public static Builder createBuilder(){
                return new Builder();
        }

        public static Builder createBuilder(String language, Command command, String apiKey, String apiLogin){
                Builder builder = new Builder();
                builder.withLanguage(language);
                builder.withCommand(command);
                Merchant merchant = new Merchant();
                merchant.setApiLogin(apiLogin);
                merchant.setApiKey(apiKey);
                builder.withMerchant(merchant);
                return  builder;
        }

        public static class Builder{
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

                /**
                 * The Credit Card Token
                 */
                CreditCardToken creditCardToken;

                /**
                 * The Credit Card Token Information
                 */
                CreditCardTokenInformation creditCardTokenInformation;

                private boolean test;

                public Builder withLanguage(String language){
                        this.language = language;
                        return this;
                }

                public Builder withCommand(Command command){
                        this.command = command;
                        return this;
                }

                public Builder withMerchant(Merchant merchant){
                        this.merchant = merchant;
                        return this;
                }

                public Builder withTransaction(Transaction transaction){
                        this.transaction = transaction;
                        return this;
                }

                public Builder withTest(boolean test){
                        this.test = test;
                        return this;
                }

                public Builder withCreditCardToken(CreditCardToken creditCardToken){
                        this.creditCardToken = creditCardToken;
                        return this;
                }

                public Builder withCreditCardTokenInformation(CreditCardTokenInformation creditCardTokenInformation){
                        this.creditCardTokenInformation = creditCardTokenInformation;
                        return this;
                }

                public PayuRequest build(){
                        return new PayuRequest(this);
                }
        }
}
