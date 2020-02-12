package com.afu.virtualshop.services;

import com.afu.virtualshop.models.ProductCategory;

import java.util.List;

public interface IProductCategoryService {

    List<ProductCategory> findAll();
}
