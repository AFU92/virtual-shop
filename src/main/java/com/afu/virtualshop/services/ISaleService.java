package com.afu.virtualshop.services;

import com.afu.virtualshop.models.Sale;

import java.util.List;

/**
 * The interface Sale service.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public interface ISaleService {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Sale> findAll();

    /**
     * Find by id sale.
     *
     * @param saleId the sale id
     * @return the sale
     */
    Sale findById(Integer saleId);

    /**
     * Find by customer list.
     *
     * @param customerId the customer id
     * @return the list
     */
    List<Sale> findByCustomer(Integer customerId);

    /**
     * Update sale.
     *
     * @param sale the sale
     * @return the sale
     */
    Sale update(Sale sale);

    /**
     * Create sale.
     *
     * @param newSale the new sale
     * @return the sale
     */
    Sale create(Sale newSale);

    /**
     * Delete by id.
     *
     * @param saleId the sale id
     */
    void deleteById(Integer saleId);
}
