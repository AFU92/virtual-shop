package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.repositories.SaleRepository;
import com.afu.virtualshop.services.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Sale service.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService {

    private final SaleRepository saleRepository;

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll().stream().filter(sale -> sale.getDeletedAt() != null).collect(Collectors.toList());
    }

    @Override
    public Sale findById(Integer saleId) {
        return saleRepository.findByIdAndNotDeleted(saleId).orElseThrow(() -> new NotFoundException("Sale with Id " + saleId + " not found"));
    }

    @Override
    public List<Sale> findByCustomer(Integer customer_id) {
        return saleRepository.findByCustomerId(customer_id);
    }

    @Override
    public Sale update(Sale sale) {
        this.findById(sale.getId());
        return saleRepository.save(sale);
    }

    @Override
    public Sale create(Sale newSale) {
        return saleRepository.save(newSale);
    }

    @Override
    public void deleteById(Integer saleId) {
        Sale sale = findById(saleId);
        sale.setDeletedAt((Timestamp) new Date());
        this.saleRepository.save(sale);
    }
}
