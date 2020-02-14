package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.SaleRefundedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRefundedProductRepository extends JpaRepository<SaleRefundedProduct, Integer> {
}
