package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findBy(String name);

    List<Product> findByProductCategory(List<String> productCategory);

}
