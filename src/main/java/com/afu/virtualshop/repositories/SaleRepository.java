package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
