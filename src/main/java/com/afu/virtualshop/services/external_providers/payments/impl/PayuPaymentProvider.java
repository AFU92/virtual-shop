package com.afu.virtualshop.services.external_providers.payments.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.exceptions.ProviderException;
import com.afu.virtualshop.models.*;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.models.payu_integration.CreditCardToken;
import com.afu.virtualshop.models.payu_integration.*;
import com.afu.virtualshop.services.external_providers.payments.PaymentProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * The type Payu payment provider.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Service
@RequiredArgsConstructor
public class PayuPaymentProvider implements PaymentProvider {

        @Value("${external-providers.payments.payu.apikey}")
        private String API_KEY;

        @Value("${external-providers.payments.payu.apilogin}")
        private String API_LOGIN;

        @Value("${external-providers.payments.payu.account-id}")
        private String ACCOUNT_ID;

        @Value("${external-providers.payments.payu.service-url}")
        private String SERVICE_URL;

        /** The rest template. */
        private final RestTemplate restTemplate;

        /**
         * The constant FORMAT_DESCRIPTION.
         */
        private static final String FORMAT_DESCRIPTION = "%s-%s";

        /**
         * The constant NOT_NULL_ERROR_MESSAGE
         */
        private static final String NOT_NULL_ERROR_MESSAGE = "The %s must not be null";

        /**
         * The constant COUNTRY.
         */
        private static final String COUNTRY = "CO";

        /**
         * The constant LANGUAGE.
         */
        private static final String LANGUAGE = "es";

        /**
         * The constant SPLIT_ADDRESS.
         */
        private static final String SPLIT_ADDRESS = ",";

        /**
         * The constant CURRENCY.
         */
        public static final String CURRENCY = "COP";

        @Override
        public Sale createPayment(Sale sale, PaymentInfo paymentInfo) {
                //Just in case it wasn't called previously
                validatePaymentParams(sale,  paymentInfo);
                PayuRequest payuRequest = createAuthorizationAndCaptureRequest(sale, paymentInfo);

                PayuResponse payuResponse = this.sendRequestToPayu(payuRequest);

                validateResponse(sale, payuRequest, payuResponse);
                return sale;
        }

        @Override
        public void validatePaymentParams(Sale sale, PaymentInfo paymentInfo) {
                Validate.notNull(sale, String.format(NOT_NULL_ERROR_MESSAGE, "sale"));
                Validate.notNull(paymentInfo, String.format(NOT_NULL_ERROR_MESSAGE, "payment Info"));
                Validate.notNull(sale.getTotalPrice(), String.format(NOT_NULL_ERROR_MESSAGE, "sale's total price"));
                Validate.notNull(sale.getCustomer(), String.format(NOT_NULL_ERROR_MESSAGE, "sale's customer"));
                Validate.notNull(sale.getCustomer().getFullName(), String.format(NOT_NULL_ERROR_MESSAGE, "sale's customer's full name"));
                Validate.notNull(sale.getCustomer().getEmail(), String.format(NOT_NULL_ERROR_MESSAGE, "sale's customer's email"));
                Validate.notNull(sale.getCustomer().getPhone(), String.format(NOT_NULL_ERROR_MESSAGE, "sale's customer's phone"));
                Validate.notNull(sale.getCustomer().getDocumentNumber(), String.format(NOT_NULL_ERROR_MESSAGE, "sale's customer's document number"));
                Validate.matchesPattern(sale.getShippingAddress(), ".*,[ ]{0,1}\\w*", "The address doesnt have the right format, Ex: Address, City");
                Validate.notNull(paymentInfo.getInstallmentsNumber(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's installments number"));
                if(paymentInfo.getToken() == null){
                        Validate.notNull(paymentInfo.getCreditCardNumber(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's credit card number"));
                        Validate.notNull(paymentInfo.getCreditCardExpirationDate(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's credit card expiration date"));
                        Validate.notNull(paymentInfo.getCreditCardCVV(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's credit card cvv"));
                }
        }

        @Override
        public Sale refundPayment(Sale sale) {
                //Just in case it wasn't called previously
                validateRefundParams(sale);
                PayuRequest payuRequest = createRefundRequest(sale);
                PayuResponse payuResponse = this.sendRequestToPayu(payuRequest);
                validateResponse(sale, payuRequest, payuResponse);
                return sale;
        }

        @Override
        public void validateRefundParams(Sale sale){
                Validate.notNull(sale, String.format(NOT_NULL_ERROR_MESSAGE, "sale"));
                Validate.notNull(sale.getRefundReason(), String.format(NOT_NULL_ERROR_MESSAGE, "refund reason"));
                Validate.notNull(sale.getProviderTransactions(), String.format(NOT_NULL_ERROR_MESSAGE, "provider transactions"));
        }

        @Override
        public Sale queryPayment(Sale sale) {
                return null;
        }

        @Override
        public com.afu.virtualshop.models.CreditCardToken createToken(PaymentInfo paymentInfo, Customer customer) {
                validateTokenParams(paymentInfo, customer);
                PayuRequest payuRequest = this.createTokenRequest(paymentInfo, customer);
                PayuResponse payuResponse = this.sendRequestToPayu(payuRequest);
                return validateTokenResponse(paymentInfo, customer, payuResponse);
        }

        private com.afu.virtualshop.models.CreditCardToken validateTokenResponse(PaymentInfo paymentInfo, Customer customer, PayuResponse payuResponse){
                if (payuResponse.getCode().equals("SUCCESS")){
                        com.afu.virtualshop.models.CreditCardToken creditCardToken = new com.afu.virtualshop.models.CreditCardToken();
                        creditCardToken.setCustomer(customer);
                        creditCardToken.setPaymentMethod(paymentInfo.getPaymentMethod());
                        creditCardToken.setProvider(Provider.PAYU);
                        creditCardToken.setToken(payuResponse.getCreditCardToken().getCreditCardTokenId());
                        return creditCardToken;
                } else {
                        throw new ProviderException(payuResponse.getCreditCardToken().getErrorDescription());
                }
        }

        private void validateTokenParams(PaymentInfo paymentInfo, Customer customer) {
                Validate.notNull(paymentInfo, String.format(NOT_NULL_ERROR_MESSAGE, "payment Info"));
                Validate.notNull(paymentInfo.getCreditCardNumber(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's credit card number"));
                Validate.notNull(paymentInfo.getCreditCardCVV(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's credit card cvv"));
                Validate.notNull(paymentInfo.getCreditCardExpirationDate(), String.format(NOT_NULL_ERROR_MESSAGE, "payment info's credit card expiration date"));
                Validate.notNull(customer, String.format(NOT_NULL_ERROR_MESSAGE, "customer"));
                Validate.notNull(customer.getId(), String.format(NOT_NULL_ERROR_MESSAGE, "customer's id"));
                Validate.notNull(customer.getFullName(), String.format(NOT_NULL_ERROR_MESSAGE, "customer's name"));
                Validate.notNull(customer.getDocumentNumber(), String.format(NOT_NULL_ERROR_MESSAGE, "customer's document number"));
        }

        @Override
        public PaymentInfo getTokenInfo(com.afu.virtualshop.models.CreditCardToken creditCardToken) {
                this.validateGetTokenInfoParams(creditCardToken);
                PayuRequest payuRequest = this.createGetTokenRequest(creditCardToken);
                PayuResponse payuResponse = this.sendRequestToPayu(payuRequest);
                return validateGetTokenInfoResponse(payuResponse);
        }

        private PaymentInfo validateGetTokenInfoResponse(PayuResponse payuResponse) {
                if (payuResponse.getCode().equals("SUCCESS")){
                        CreditCardTokenResponse creditCardTokenResponse = payuResponse.getCreditCardTokenList().stream().findFirst().orElseThrow(() -> new ProviderException("Token not found in provider!"));
                        PaymentInfo paymentInfo = new PaymentInfo();
                        paymentInfo.setPrintableCreditCardCVV(creditCardTokenResponse.getMaskedNumber());
                        paymentInfo.setPaymentMethod(creditCardTokenResponse.getPaymentMethod());
                        paymentInfo.setToken(creditCardTokenResponse.getCreditCardTokenId());
                        return paymentInfo;
                } else {
                        throw new ProviderException(payuResponse.getCreditCardToken().getErrorDescription());
                }
        }

        private void validateGetTokenInfoParams(com.afu.virtualshop.models.CreditCardToken creditCardToken){
                Validate.notNull(creditCardToken, String.format(NOT_NULL_ERROR_MESSAGE, "credit card Token"));
                Validate.notNull(creditCardToken.getToken(), String.format(NOT_NULL_ERROR_MESSAGE, "credit card Token info"));
                Validate.notNull(creditCardToken.getCustomer(), String.format(NOT_NULL_ERROR_MESSAGE, "credit card Token customer"));
                Validate.notNull(creditCardToken.getCustomer().getId(), String.format(NOT_NULL_ERROR_MESSAGE, "credit card Token customer id"));
        }

        private PayuRequest createGetTokenRequest(com.afu.virtualshop.models.CreditCardToken creditCardToken){
                CreditCardTokenInformation creditCardTokenInformation = CreditCardTokenInformation.builder()
                        .creditCardTokenId(creditCardToken.getToken())
                        .payerId(creditCardToken.getCustomer().getId().toString())
                        .build();

                return PayuRequest.createBuilder(LANGUAGE, Command.GET_TOKENS, API_KEY, API_LOGIN)
                        .withCreditCardTokenInformation(creditCardTokenInformation)
                        .build();
        }

        private PayuRequest createAuthorizationAndCaptureRequest(Sale sale, PaymentInfo paymentInfo) {
                Order order = Order.createBuilder().withAccountId(ACCOUNT_ID).withReferenceCode(sale.getId().toString())
                                .withDescription(String.format(FORMAT_DESCRIPTION, sale.getId(),
                                                System.currentTimeMillis()))
                                .withLanguage(LANGUAGE).addAdditionalValue(AdditionalValueType.TX_VALUE,
                                                new AdditionalValue(sale.getTotalPrice(), CURRENCY))
                                .build();

                String[] fullAddress = sale.getShippingAddress().split(SPLIT_ADDRESS);
                Address billingAddress = Address.createBuilder().withStreet1(fullAddress[0]).withCity(fullAddress[1])
                                .withCountry(COUNTRY).withPhone(sale.getCustomer().getPhone()).build();

                Payer payer = Payer.createBuilder().withFullName(sale.getCustomer().getFullName())
                                .withEmailAddress(sale.getCustomer().getEmail())
                                .withContactPhone(sale.getCustomer().getPhone())
                                .withDniNumber(sale.getCustomer().getDocumentNumber())
                                .withBillingAddress(billingAddress).build();

                Transaction.Builder transactionBuilder = Transaction.createBuilder();
                if(paymentInfo.getToken() == null){
                        CreditCard creditCard = CreditCard.createBuilder().withName(sale.getCustomer().getFullName())
                                .withNumber(paymentInfo.getCreditCardNumber())
                                .withExpirationDate(paymentInfo.getCreditCardExpirationDate())
                                .withSecurityCode(paymentInfo.getCreditCardCVV()).build();
                        transactionBuilder.withCreditCard(creditCard);
                } else {
                        transactionBuilder.withCreditCardTokenId(paymentInfo.getToken());
                }

                Transaction transaction = transactionBuilder.withOrder(order)
                                .addExtraParameter(ExtraParameter.INSTALLMENTS_NUMBER, paymentInfo.getInstallmentsNumber())
                                .withType(TransactionType.AUTHORIZATION_AND_CAPTURE)
                                .withPaymentMethod(paymentInfo.getPaymentMethod().name()).withPaymentCountry(COUNTRY)
                                .withPayer(payer).build();

                return PayuRequest.createBuilder(LANGUAGE, Command.SUBMIT_TRANSACTION, API_KEY, API_LOGIN)
                                .withTest(false).withTransaction(transaction).build();
        }

        private PayuRequest createRefundRequest(Sale sale) {
                Order order = Order.createBuilder().withId(sale.getExternalSaleId()).build();

                Transaction transaction = Transaction.createBuilder().withOrder(order).withType(TransactionType.REFUND)
                                .withReason(sale.getRefundReason())
                                .withParentTransactionId(this.getCaptureTransaction(sale).getProviderTransactionId())
                                .build();

                return PayuRequest.createBuilder(LANGUAGE, Command.SUBMIT_TRANSACTION, API_KEY, API_LOGIN)
                                .withTransaction(transaction).withTest(false).build();
        }

        private PayuRequest createTokenRequest(PaymentInfo paymentInfo, Customer customer){

                CreditCardToken creditCardToken = CreditCardToken.builder()
                        .payerId(customer.getId().toString())
                        .name(customer.getFullName())
                        .identificationNumber(customer.getDocumentNumber())
                        .paymentMethod(paymentInfo.getPaymentMethod().name())
                        .number(paymentInfo.getCreditCardNumber())
                        .expirationDate(paymentInfo.getCreditCardExpirationDate())
                        .build();
                return PayuRequest.createBuilder(LANGUAGE, Command.SUBMIT_TRANSACTION, API_KEY, API_LOGIN)
                        .withCommand(Command.CREATE_TOKEN)
                        .withCreditCardToken(creditCardToken)
                        .build();
        }

        /**
         * Find the providerTransaction with type AUTHORIZATION_AND_CAPTURE and result
         * APPROVED of the sale
         * 
         * @param sale The sale
         * @return providerTransaction with type AUTHORIZATION_AND_CAPTURE and result
         *         APPROVED
         */
        private ProviderTransaction getCaptureTransaction(Sale sale) {
                return sale.getProviderTransactions().stream()
                                .filter(providerTransaction -> providerTransaction.getType()
                                                .equals(TransactionType.AUTHORIZATION_AND_CAPTURE.name())
                                                && providerTransaction.getResult().equals(TransactionResult.APPROVED))
                                .findFirst().orElseThrow(() -> new NotFoundException(
                                                "There is no approved capture for the sale " + sale.getId()));
        }

        private PayuResponse sendRequestToPayu(PayuRequest payuRequest){
                HttpEntity<PayuRequest> request = new HttpEntity<>(payuRequest);
                ResponseEntity<PayuResponse> response = restTemplate.exchange(SERVICE_URL, HttpMethod.POST, request,
                        PayuResponse.class);
                return response.getBody();
        }

        private void validateResponse(Sale sale, PayuRequest payuRequest, PayuResponse payuResponse) {
                ProviderTransaction providerTransaction = new ProviderTransaction();
                //providerTransaction.setProviderResponse(new ObjectMapper().convertValue(payuResponse, Map.class));
                providerTransaction.setType(payuRequest.getTransaction().getType().name());
                providerTransaction.setSale(sale);

                if (payuResponse.getCode().equals("SUCCESS")){
                        providerTransaction.setProviderTransactionId(payuResponse.getTransactionResponse().getTransactionId());
                        ResultMapper resultMapper =  ResultMapper.getByTransactionTypeAndResult(payuRequest.getTransaction().getType(), payuResponse.getTransactionResponse().getState());
                        providerTransaction.setResult(resultMapper.getTransactionResult());
                        sale.setStatus(resultMapper.getSaleStatus());
                } else {
                        providerTransaction.setResult(TransactionResult.REJECTED);
                }
                sale.setExternalSaleId(payuResponse.getTransactionResponse().getOrderId());
                sale.getProviderTransactions().add(providerTransaction);
        }

}
