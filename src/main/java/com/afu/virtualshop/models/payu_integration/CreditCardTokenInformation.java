package com.afu.virtualshop.models.payu_integration;

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
