package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.SaleStatus;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.repositories.SaleRepository;
import com.afu.virtualshop.services.ICustomerService;
import com.afu.virtualshop.services.IProductService;
import com.afu.virtualshop.services.ISaleService;
import com.afu.virtualshop.services.external_providers.payments.PaymentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private final IProductService productService;
    private final PaymentProvider paymentProvider;
    private final ICustomerService customerService;

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
    public Sale create(Sale newSale, PaymentInfo paymentInfo) {
        this.validateSaleProducts(newSale);
        Customer customer =  this.customerService.findById(newSale.getCustomer().getId());
        newSale.setCustomer(customer);
        this.paymentProvider.validatePaymentParams(newSale, paymentInfo);
        newSale.setStatus(SaleStatus.CREATED);
        saleRepository.save(newSale);
        this.paymentProvider.createPayment(newSale, paymentInfo);
        return saleRepository.save(paymentProvider.createPayment(newSale, paymentInfo));
    }

    private void validateSaleProducts(Sale sale){
        List<String> notAvailableProducts = new ArrayList<>();
        sale.getSaleProducts().forEach(saleProduct -> {
            if (productService.validateStock(saleProduct.getProduct().getId(), saleProduct.getQuantity())==false){
                String message = saleProduct.getProduct().getId()+ "" + saleProduct.getQuantity();
                notAvailableProducts.add(message);
            } else{
                saleProduct.setTotalPrice(saleProduct.getQuantity() * saleProduct.getUnitPrice());
                sale.setTotalPrice(sale.getTotalPrice() != null ? sale.getTotalPrice() + saleProduct.getTotalPrice() : saleProduct.getTotalPrice());
            }
        });
        if (!notAvailableProducts.isEmpty()){
            new IllegalArgumentException("The following products are not available: " + notAvailableProducts.toString());
        }
    }

    @Override
    public void deleteById(Integer saleId) {
        Sale sale = findById(saleId);
        sale.setDeletedAt((Timestamp) new Date());
        this.saleRepository.save(sale);
    }
}
