package com.afu.virtualshop.documentationControllers;

import com.afu.virtualshop.models.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductDocumentation {

	@ApiOperation("Get all products")
	ResponseEntity<List<Product>> findAll();


	@ApiOperation("Get a Product by id")
	ResponseEntity<Product> findById(@PathVariable Integer id);


	@ApiOperation("Update a Product")
	ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product);


	@ApiOperation("Create a Product")
	ResponseEntity<Product> create(@RequestBody Product product);


	@ApiOperation("Delete a Product")
	ResponseEntity delete(@PathVariable Integer id);

}


