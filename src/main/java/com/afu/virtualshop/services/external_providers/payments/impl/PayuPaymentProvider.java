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
import java.util.HashMap;
import java.util.Map;

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

        PayuRequest payuRequest = new PayuRequest();

        payuRequest.setLanguage(LANGUAGE);
        payuRequest.setCommand(Command.SUBMIT_TRANSACTION);

        Merchant merchant = new Merchant();

        merchant.setApiKey(API_KEY);
        merchant.setApiLogin(API_LOGIN);

        Transaction transaction = new Transaction();

        Map<ExtraParameter, Object> extraParameters = new HashMap<>();
        extraParameters.put(ExtraParameter.INSTALLMENTS, paymentInfo.getInstallmentsNumber());
        transaction.setExtraParameters(extraParameters);

        transaction.setType(TransactionType.AUTHORIZATION_AND_CAPTURE);
        transaction.setPaymentMethod(paymentInfo.getPaymentMethod().name());
        transaction.setPaymentCountry(COUNTRY);

        Order order = new Order();

        order.setAccountId(ACCOUNT_ID);
        order.setReferenceCode(sale.getId().toString());
        order.setDescription(String.format(FORMAT_DESCRIPTION, sale.getId(), System.currentTimeMillis()));
        order.setLanguage(LANGUAGE);

        Map<AdditionalValueType, AdditionalValue> additionalValues = new HashMap<>();
        additionalValues.put(AdditionalValueType.TX_VALUE, new AdditionalValue(sale.getTotalPrice(), CURRENCY));

        order.setAdditionalValues(additionalValues);

        CreditCard creditCard = new CreditCard();
        creditCard.setName(sale.getCustomer().getFullName());
        creditCard.setNumber(paymentInfo.getCreditCardNumber());
        creditCard.setExpirationDate(paymentInfo.getCreditCardExpirationDate());
        creditCard.setSecurityCode(paymentInfo.getCreditCardCVV());

        transaction.setCreditCard(creditCard);
        payuRequest.setTest(false);

        Payer payer = new Payer();

        payer.setFullName(sale.getCustomer().getFullName());
        payer.setEmailAddress(sale.getCustomer().getEmail());
        payer.setContactPhone(sale.getCustomer().getPhone());
        payer.setDniNumber(sale.getCustomer().getDocumentNumber());

        Address billingAddress = new Address();

        String[] fullAddress = sale.getCustomer().getAddress().split(SPLIT_ADDRESS);
        billingAddress.setStreet1(fullAddress[0]);
        billingAddress.setCity(fullAddress[1]);
        billingAddress.setCountry(COUNTRY);
        billingAddress.setPhone(sale.getCustomer().getPhone());

        payer.setBillingAddress(billingAddress);

        transaction.setPayer(payer);

        HttpEntity<PayuRequest> request = new HttpEntity<>(payuRequest);
        ResponseEntity<PayuResponse> response = restTemplate.exchange(SERVICE_URL, HttpMethod.POST, request,
                PayuResponse.class);

        // ToDo read response and update sale with PayU's orderId, create a new
        // ProviderTransaction and save it in Sale

        return sale;
    }

    @Override
    public Sale refundPayment(Sale sale) {
        PayuRefund payuRefund = new PayuRefund();

        payuRefund.setLanguage(LANGUAGE);
        payuRefund.setCommand(Command.SUBMIT_TRANSACTION);

        Merchant merchant = new Merchant();

        merchant.setApiKey(API_KEY);
        merchant.setApiLogin(API_LOGIN);

        Transaction transaction = new Transaction();

        Order order = new Order();

        order.setId(sale.getExternalSaleId());
        transaction.setOrder(order);

        transaction.setType(TransactionType.REFUND);
        transaction.setReason(sale.getRefundReason());
        transaction.setParentTransactionId(this.getCaptureTransaction(sale).getProviderTransactionId());

        payuRefund.setTransaction(transaction);
        payuRefund.setTest(false);

        return sale;

    }

    private ProviderTransaction getCaptureTransaction(Sale sale){
        return sale.getProviderTransactions()
                .stream()
                .filter(providerTransaction -> providerTransaction.getType().equals(TransactionType.AUTHORIZATION_AND_CAPTURE) &&
                        providerTransaction.getResult().equals(TransactionResult.APPROVED))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("There is no approved capture for the sale " + sale.getId()));
    }

    @Override
    public Sale queryPayment(Sale sale) {
        return null;
    }



}
