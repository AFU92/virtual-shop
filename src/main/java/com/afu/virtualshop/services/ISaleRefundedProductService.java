package com.afu.virtualshop.services;

import com.afu.virtualshop.models.SaleProduct;
import com.afu.virtualshop.models.SaleRefundedProduct;

import java.util.List;

public interface ISaleRefundedProductService {

    List<SaleRefundedProduct> saveAll(List<SaleRefundedProduct> saleRefundedProductListProductList);
}
