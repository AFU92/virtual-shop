package com.afu.virtualshop.documentationControllers;

import com.afu.virtualshop.models.Sale;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;


public interface SaleDocumentation {

	@ApiOperation("Get all sales")
	ResponseEntity findAll();


	@ApiOperation("Get all sales")
	ResponseEntity findById();


	@ApiOperation("Get all sales")
	ResponseEntity<Sale> update();


	@ApiOperation("Get all sales")
	ResponseEntity <Sale> create();


	@ApiOperation("Get all sales")
	ResponseEntity <Sale> refund();


	@ApiOperation("Get all sales")
	ResponseEntity delete();

}


