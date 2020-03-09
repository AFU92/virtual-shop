package com.afu.virtualshop.controllers;

import com.afu.virtualshop.documentationControllers.SaleDocumentation;
import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.api.RefundRequest;
import com.afu.virtualshop.models.api.SaleRequest;
import com.afu.virtualshop.services.ISaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Sale controller.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@RestController
@RequestMapping("/sales")
@Slf4j
@RequiredArgsConstructor
public class SaleController implements SaleDocumentation {

    private final ISaleService saleService;

    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.ok(saleService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Integer id){
        return ResponseEntity.ok(saleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> update(@PathVariable Sale sale){
        return ResponseEntity.ok(saleService.update(sale));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody SaleRequest saleRequest){
        Sale newSale = saleService.create(saleRequest.getSale(), saleRequest.getPaymentInfo());
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }

    @PostMapping("/{saleId}/refund")
    public ResponseEntity<Sale> refund(@PathVariable Integer saleId, @RequestBody RefundRequest refundRequest){
        return ResponseEntity.ok(this.saleService.refund(saleId, refundRequest));
    }

}
