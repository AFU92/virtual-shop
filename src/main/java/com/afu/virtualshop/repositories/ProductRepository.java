package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Product repository.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Product> findByName(String name);

    /**
     * Find by product category list.
     *
     * @param productCategory the product category
     * @return the list
     */
    List<Product> findByProductCategory(ProductCategory productCategory);

}
