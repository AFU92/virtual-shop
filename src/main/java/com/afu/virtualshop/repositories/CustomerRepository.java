package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Customer repository.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
