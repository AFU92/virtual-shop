package com.afu.virtualshop.models.payu_integration;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.SaleStatus;
import com.afu.virtualshop.models.TransactionResult;
import lombok.AllArgsConstructor;

import java.util.Arrays;

import static com.afu.virtualshop.models.SaleStatus.PAYMENT_APPROVED;
import static com.afu.virtualshop.models.SaleStatus.PAYMENT_REJECTED;
import static com.afu.virtualshop.models.SaleStatus.REFUNDED;
import static com.afu.virtualshop.models.SaleStatus.REFUND_REJECTED;
import static com.afu.virtualshop.models.TransactionResult.APPROVED;
import static com.afu.virtualshop.models.TransactionResult.REJECTED;
import static com.afu.virtualshop.models.payu_integration.TransactionType.AUTHORIZATION_AND_CAPTURE;
import static com.afu.virtualshop.models.payu_integration.TransactionType.REFUND;

@AllArgsConstructor
public enum ResultMapper {

    AUTHORIZATION_AND_CAPTURE_APPROVED(AUTHORIZATION_AND_CAPTURE, PayuTransactionResult.APPROVED, APPROVED, PAYMENT_APPROVED),
    AUTHORIZATION_AND_CAPTURE_DECLINED(AUTHORIZATION_AND_CAPTURE, PayuTransactionResult.DECLINED, REJECTED, PAYMENT_REJECTED),
    REFUND_APPROVED(REFUND, PayuTransactionResult.APPROVED, APPROVED, REFUNDED),
    REFUND_DECLINED(REFUND, PayuTransactionResult.DECLINED, REJECTED, REFUND_REJECTED);

    private TransactionType transactionType;
    private PayuTransactionResult payuTransactionResult;
    private TransactionResult transactionResult;
    private SaleStatus saleStatus;

    public TransactionType getTransactionType(){
        return this.transactionType;
    }

    public TransactionResult getTransactionResult(){
        return this.transactionResult;
    }

    public SaleStatus getSaleStatus(){
        return this.saleStatus;
    }

    public static ResultMapper getByTransactionTypeAndResult(TransactionType transactionType, PayuTransactionResult payuTransactionResult){
        return Arrays.stream(
                ResultMapper.values())
                .filter(resultMapper -> resultMapper.payuTransactionResult.equals(payuTransactionResult) && resultMapper.transactionType.equals(transactionType))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Result Mapper not found for transactionType: " + transactionType + " and result: " + payuTransactionResult));
    }
}
