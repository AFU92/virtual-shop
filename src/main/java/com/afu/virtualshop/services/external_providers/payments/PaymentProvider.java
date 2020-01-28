package com.afu.virtualshop.services.external_providers.payments;

import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.api.PaymentInfo;

public interface PaymentProvider {

    Sale createPayment(Sale sale, PaymentInfo paymentInfo);

    Sale refundPayment(Sale sale);

    Sale queryPayment(Sale sale);
}
