package com.afu.virtualshop.models.api;

import com.afu.virtualshop.models.SaleRefundedProduct;
import lombok.Data;

import java.util.List;

@Data
public class RefundRequest {
    private String refundReason;
    private List<SaleRefundedProduct> saleRefundedProducts;
}
