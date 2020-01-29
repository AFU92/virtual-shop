package com.afu.virtualshop.services.external_providers.payments.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.ProviderTransaction;
import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.TransactionResult;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.models.payu_integration.*;
import com.afu.virtualshop.services.external_providers.payments.PaymentProvider;
import lombok.RequiredArgsConstructor;
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
        private RestTemplate restTemplate;

        /**
         * The constant FORMAT_DESCRIPTION.
         */
        private static final String FORMAT_DESCRIPTION = "%s-%s";

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
        private static final String SPLIT_ADDRESS = ";";

        /**
         * The constant CURRENCY.
         */
        public static final String CURRENCY = "COP";

        /**
         * Inits the.
         */
        @PostConstruct
        public void init() {
                restTemplate = new RestTemplate();
        }

        @Override
        public Sale createPayment(Sale sale, PaymentInfo paymentInfo) {
                validateAuthorizationAndCaptureParams(sale,  paymentInfo);
                PayuRequest payuRequest = createAuthorizationAndCaptureRequest(sale, paymentInfo);

                PayuResponse payuResponse = this.sendRequestToPayu(payuRequest);
                return sale;
        }

        @Override
        public Sale refundPayment(Sale sale) {
                validateRefundParams(sale);
                PayuRequest payuRequest = createRefundRequest(sale);

                PayuResponse payuResponse = this.sendRequestToPayu(payuRequest);
                return sale;

        }

        @Override
        public Sale queryPayment(Sale sale) {
                return null;
        }

        private PayuRequest createAuthorizationAndCaptureRequest(Sale sale, PaymentInfo paymentInfo) {
                Order order = Order.createBuilder().withAccountId(ACCOUNT_ID).withReferenceCode(sale.getId().toString())
                                .withDescription(String.format(FORMAT_DESCRIPTION, sale.getId(),
                                                System.currentTimeMillis()))
                                .withLanguage(LANGUAGE).addAdditionalValue(AdditionalValueType.TX_VALUE,
                                                new AdditionalValue(sale.getTotalPrice(), CURRENCY))
                                .build();

                CreditCard creditCard = CreditCard.createBuilder().withName(sale.getCustomer().getFullName())
                                .withNumber(paymentInfo.getCreditCardNumber())
                                .withExpirationDate(paymentInfo.getCreditCardExpirationDate())
                                .withSecurityCode(paymentInfo.getCreditCardCVV()).build();

                String[] fullAddress = sale.getCustomer().getAddress().split(SPLIT_ADDRESS);
                Address billingAddress = Address.createBuilder().withStreet1(fullAddress[0]).withCity(fullAddress[1])
                                .withCountry(COUNTRY).withPhone(sale.getCustomer().getPhone()).build();

                Payer payer = Payer.createBuilder().withFullName(sale.getCustomer().getFullName())
                                .withEmailAddress(sale.getCustomer().getEmail())
                                .withContactPhone(sale.getCustomer().getPhone())
                                .withDniNumber(sale.getCustomer().getDocumentNumber())
                                .withBillingAddress(billingAddress).build();

                Transaction transaction = Transaction.createBuilder().withCreditCard(creditCard).withOrder(order)
                                .addExtraParameter(ExtraParameter.INSTALLMENTS, paymentInfo.getInstallmentsNumber())
                                .withType(TransactionType.AUTHORIZATION_AND_CAPTURE)
                                .withPaymentMethod(paymentInfo.getPaymentMethod().name()).withPaymentCountry(COUNTRY)
                                .withPayer(payer).build();

                return PayuRequest.createBuilder(LANGUAGE, Command.SUBMIT_TRANSACTION, API_KEY, API_LOGIN)
                                .withTest(false).withTransaction(transaction).build();
        }

        private void validateAuthorizationAndCaptureParams(Sale sale, PaymentInfo paymentInfo){

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

        private void validateRefundParams(Sale sale){

        }

        /**
         * Find the providerTransaction with type AUTHORIZATION_AND_CAPTURE and result
         * APPROVED of the sale
         * 
         * @param sale
         * @return providerTransaction with type AUTHORIZATION_AND_CAPTURE and result
         *         APPROVED
         */
        private ProviderTransaction getCaptureTransaction(Sale sale) {
                return sale.getProviderTransactions().stream()
                                .filter(providerTransaction -> providerTransaction.getType()
                                                .equals(TransactionType.AUTHORIZATION_AND_CAPTURE)
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

}
