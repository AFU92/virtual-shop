package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * The interface Sale repository.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findByCustomerId(Integer customerId);

    @Query("FROM Sale s WHERE s.id = ?1 and s.deletedAt is not null")
    Optional<Sale> findByIdAndNotDeleted(Integer id);

}
