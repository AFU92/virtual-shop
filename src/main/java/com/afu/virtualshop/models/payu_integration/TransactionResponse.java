package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

import java.util.Map;

@Data
public class TransactionResponse {
    private String orderId;
    private String transactionId;
    private String state;
    private String paymentNetworkResponseCode;
    private String paymentNetworkResponseErrorMessage;
    private String trazabilityCode;
    private String pendingReason;
    private String responseCode;
    private String errorCode;
    private String responseMessage;
    private String transactionDate;
    private String operationDate;
    private Map<String, String> extraParameters;
}
