package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.*;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.models.api.RefundRequest;
import com.afu.virtualshop.repositories.SaleRepository;
import com.afu.virtualshop.services.*;
import com.afu.virtualshop.services.external_providers.payments.PaymentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private final IProviderTransactionService providerTransactionService;
    private final ISaleProductService saleProductService;
    private final ISaleRefundedProductService saleRefundedProductService;

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public Sale findById(Integer saleId) {
        return saleRepository.findById(saleId)
                             .orElseThrow(() -> new NotFoundException("Sale with Id " + saleId + " not found"));
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
        this.saleProductService.saveAll(newSale.getSaleProducts());
        this.paymentProvider.createPayment(newSale, paymentInfo);
        this.providerTransactionService.saveAllProviderTransactions(newSale.getProviderTransactions());
        this.validateProductsStockUpdate(newSale);
        return saleRepository.save(newSale);
    }

    private void validateProductsStockUpdate(Sale sale){
        if (sale.getStatus().equals(SaleStatus.PAYMENT_APPROVED)){
            sale.getSaleProducts().forEach(saleProduct -> {
                this.productService.reduceProductStock(saleProduct.getProduct().getId(), saleProduct.getQuantity());
            });
        }
    }

    private void validateSaleProducts(Sale sale){
        List<String> notAvailableProducts = new ArrayList<>();
        sale.getSaleProducts().forEach(saleProduct -> {
            Product product = productService.findById(saleProduct.getProduct().getId());
            saleProduct.setSale(sale);
            if (productService.validateStock(saleProduct.getProduct().getId(), saleProduct.getQuantity())==false){
                String message = saleProduct.getProduct().getId()+ "" + saleProduct.getQuantity();
                notAvailableProducts.add(message);
            } else{
                saleProduct.setTotalPrice(saleProduct.getQuantity() * product.getUnitPrice());
                sale.setTotalPrice(sale.getTotalPrice() != null ? sale.getTotalPrice() + saleProduct.getTotalPrice() : saleProduct.getTotalPrice());
            }
        });
        if (!notAvailableProducts.isEmpty()){
            throw new IllegalArgumentException("The following products are not available: " + notAvailableProducts.toString());
        }
    }

    @Override
    public Sale refund(Integer saleId, RefundRequest refundRequest) {
        Sale sale = this.findById(saleId);
        if(!sale.getStatus().equals(SaleStatus.PAYMENT_APPROVED)){
            throw new IllegalArgumentException("Sale with id: " + saleId + " doesn't have the correct status to be refunded");
        }
        sale.setRefundReason(refundRequest.getRefundReason());
        this.associateProductRefundedProductsToSale(sale, refundRequest);
        this.paymentProvider.refundPayment(sale);
        this.providerTransactionService.saveAllProviderTransactions(sale.getProviderTransactions());
        this.validateProductsStockRefund(sale);
        this.saleRefundedProductService.saveAll(sale.getSaleRefundedProducts());
        return this.saleRepository.save(sale);
    }

    private void associateProductRefundedProductsToSale(Sale sale, RefundRequest refundRequest){
        refundRequest.getSaleRefundedProducts().forEach(saleRefundedProduct -> {
            saleRefundedProduct.setSale(sale);
            sale.getSaleRefundedProducts().add(saleRefundedProduct);
            SaleProduct saleProduct = sale.getSaleProducts().stream().filter(saleProduct2 ->
                saleProduct2.getId() == saleRefundedProduct.getSaleProduct().getId()
            ).findFirst().orElseThrow(() -> new NotFoundException("SaleProduct with id: " + saleRefundedProduct.getSaleProduct().getId() + "not found"));
            saleRefundedProduct.setTotalPrice(saleRefundedProduct.getQuantity() * saleProduct.getUnitPrice());
            sale.setRefundValue(sale.getRefundPercent() == null ? saleRefundedProduct.getTotalPrice() : sale.getRefundValue() + saleRefundedProduct.getTotalPrice());
        });
    }

    private void validateProductsStockRefund(Sale sale){
        if (sale.getStatus().equals(SaleStatus.REFUNDED)){
            sale.getSaleRefundedProducts().forEach(saleRefundedProduct -> {
                this.productService.increaseProductStock(saleRefundedProduct.getSaleProduct().getId(), saleRefundedProduct.getQuantity());
            });
        }
    }
}
