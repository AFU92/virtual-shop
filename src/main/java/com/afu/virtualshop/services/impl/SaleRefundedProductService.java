package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.SaleRefundedProduct;
import com.afu.virtualshop.repositories.SaleRefundedProductRepository;
import com.afu.virtualshop.services.ISaleRefundedProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SaleRefundedProductService implements ISaleRefundedProductService {

    private final SaleRefundedProductRepository saleRefundedProductRepository;

    @Override
    public List<SaleRefundedProduct> saveAll(List<SaleRefundedProduct> saleRefundedProductListProductList) {
        return this.saleRefundedProductRepository.saveAll(saleRefundedProductListProductList);
    }
}
