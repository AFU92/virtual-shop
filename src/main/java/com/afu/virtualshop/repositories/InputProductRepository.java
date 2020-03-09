package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.InputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Input product repository.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {
}
