package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Sale repository.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
