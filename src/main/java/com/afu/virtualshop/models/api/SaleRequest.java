package com.afu.virtualshop.models.api;

import com.afu.virtualshop.models.Sale;
import lombok.Data;

@Data
public class SaleRequest {
    private Sale sale;
    private PaymentInfo paymentInfo;
}
