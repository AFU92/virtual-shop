package com.afu.virtualshop.services;

import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.ProductCategory;
import com.afu.virtualshop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findByProductCategory(ProductCategory productCategory){
        return productRepository.findByProductCategory(productCategory);
    }

    public Product save(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public void deleteById(Integer productId) {
        Product product = findById(productId).get();
        product.setDeletedAt((Timestamp) new Date());
        this.save (product);
    }
}
