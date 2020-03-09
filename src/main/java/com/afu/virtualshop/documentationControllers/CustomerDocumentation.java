package com.afu.virtualshop.documentationControllers;

import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.api.RefundRequest;
import com.afu.virtualshop.models.api.SaleRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface SaleDocumentation {

	@ApiOperation("Get all sales")
	ResponseEntity findAll();


	@ApiOperation("Get a sale by id")
	ResponseEntity findById(Integer id);


	@ApiOperation("Update a sale")
	ResponseEntity<Sale> update(Sale sale);


	@ApiOperation("Create a sale")
	ResponseEntity <Sale> create(SaleRequest saleRequest);


	@ApiOperation("Refund an existing sale")
	ResponseEntity <Sale> refund( Integer saleId, RefundRequest refundRequest);


	@ApiOperation("Delete a sale by id")
	ResponseEntity delete(Integer id);

}


