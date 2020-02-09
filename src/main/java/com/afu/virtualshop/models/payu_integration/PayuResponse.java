package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

@Data
public class PayuResponse {

    private String code;
    private String error;
    private TransactionResponse transactionResponse;

}
