package com.afu.virtualshop.controllers;

import com.afu.virtualshop.models.ProductCategory;
import com.afu.virtualshop.services.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Product category controller.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@RestController
@RequestMapping("/product_categories")
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryController {

    private final IProductCategoryService productCategoryService;

    /**
     * Find all list.
     *
     * @return the list
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<ProductCategory> findAll(){
        return productCategoryService.findAll();
    }

}
