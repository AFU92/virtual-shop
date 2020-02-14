package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Integer> {
}
