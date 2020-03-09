package com.afu.virtualshop.services;

import com.afu.virtualshop.models.SaleProduct;

import java.util.List;

public interface ISaleProductService {
    List<SaleProduct> saveAll(List<SaleProduct> saleProductList);
}
