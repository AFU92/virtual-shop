package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.SaleProduct;
import com.afu.virtualshop.repositories.SaleProductRepository;
import com.afu.virtualshop.services.ISaleProductService;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SaleProductService implements ISaleProductService {

    private final SaleProductRepository saleProductRepository;

    @Override
    public List<SaleProduct> saveAll(List<SaleProduct> saleProductList) {
        return this.saleProductRepository.saveAll(saleProductList);
    }
}
