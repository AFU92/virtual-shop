package com.afu.virtualshop.controllers;

import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.services.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Product controller.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    /**
     * Find all response entity.
     *
     * @param available the available
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<Product>> findAll(@RequestParam(value="available", required = false) Boolean available){
        List<Product> products;
        if (available != null && available == true){
            products = productService.findAllAvailable();
        } else {
            products = productService.findAll();
        }
        return ResponseEntity.ok(products);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Update response entity.
     *
     * @param id      the id
     * @param product the product
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(productService.update(product));
    }

    /**
     * Create response entity.
     *
     * @param product the product
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product newProduct = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //ToDo GetProductsByProductCategory EndPoint
}
