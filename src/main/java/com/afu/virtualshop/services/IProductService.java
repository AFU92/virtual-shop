package com.afu.virtualshop.services;

import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.ProductCategory;

import java.util.List;

/**
 * The interface Product service.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface IProductService {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Product> findAll();

    List<Product> findAllAvailable();

    /**
     * Find by id product.
     *
     * @param productId the product id
     * @return the product
     */
    Product findById(Integer productId);

    /**
     * Find by product category list.
     *
     * @param productCategory the product category
     * @return the list
     */
    List<Product> findByProductCategory(ProductCategory productCategory);

    /**
     * Update product.
     *
     * @param product the product
     * @return the product
     */
    Product update(Product product);

    /**
     * Create product.
     *
     * @param newProduct the new product
     * @return the product
     */
    Product create(Product newProduct);

    /**
     * Delete by id.
     *
     * @param productId the product id
     */
    void deleteById(Integer productId);

    Boolean validateStock(Integer productId, Integer quantity);

    void reduceProductStock(Integer productId, Integer quantity);
}
