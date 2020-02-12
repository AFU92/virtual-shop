package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.ProductCategory;
import com.afu.virtualshop.services.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService implements IProductCategoryService {


    @Override
    public List<ProductCategory> findAll(){
        return Arrays.asList(ProductCategory.values());
    }
}
