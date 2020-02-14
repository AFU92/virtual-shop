package com.afu.virtualshop.services.external_providers.payments.impl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardTokenInformation {
    private String payerId;
    private String creditCardTokenId;
    private String startDate;
    private String endDate;
}
