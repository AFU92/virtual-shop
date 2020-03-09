package com.afu.virtualshop.services.external_providers.payments;

import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.api.PaymentInfo;

public interface PaymentProvider {

    Sale createPayment(Sale sale, PaymentInfo paymentInfo);

    void validatePaymentParams(Sale sale, PaymentInfo paymentInfo);

    Sale refundPayment(Sale sale);

    void validateRefundParams(Sale sale);

    Sale queryPayment(Sale sale);

    CreditCardToken createToken(PaymentInfo paymentInfo, Customer customer);

    PaymentInfo getTokenInfo(CreditCardToken creditCardToken);
}
