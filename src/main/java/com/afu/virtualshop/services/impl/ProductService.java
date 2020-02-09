package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.ProductCategory;
import com.afu.virtualshop.repositories.ProductRepository;
import com.afu.virtualshop.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Product service.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public  List<Product> findAllAvailable() {
        List<Product> productStock = findAll().stream().filter(product -> product.getQuantity() > 0 && product.getDeletedAt() != null).collect(Collectors.toList());
        return productStock;
    }

    public Product findById(Integer productId) {
        return productRepository.findByIdAndNotDeleted(productId).orElseThrow(() -> new NotFoundException("Product with Id " + productId + " not found"));
    }

    public List<Product> findByProductCategory(ProductCategory productCategory){
        return productRepository.findByProductCategory(productCategory);
    }

    public Product update(Product product) {
        this.findById(product.getId());
        return productRepository.save(product);
    }

    public Product create(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public void deleteById(Integer productId) {
        Product product = findById(productId);
        product.setDeletedAt((Timestamp) new Date());
        this.productRepository.save(product);
    }

    public Boolean validateStock(Product product){
        Product existingProduct = findById(product.getId());
        return product.getQuantity() < existingProduct.getQuantity();
    }
}
