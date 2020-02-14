package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

import java.util.List;

@Data
public class PayuResponse {
    private String code;
    private String error;
    private TransactionResponse transactionResponse;
    private CreditCardTokenResponse creditCardToken;
    private List<CreditCardTokenResponse> creditCardTokenList;
}
