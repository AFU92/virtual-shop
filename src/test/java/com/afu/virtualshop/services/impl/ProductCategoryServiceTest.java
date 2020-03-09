package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.ProductCategory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProductCategoryServiceTest {

	private ProductCategoryService productCategoryService;

	@Before
	public void setup(){
		productCategoryService = new ProductCategoryService();
	}


	@Test
	public void findAll() {
		List<ProductCategory> productCategoryServiceList = this.productCategoryService.findAll();

		assertNotNull(productCategoryServiceList);
		assertTrue(productCategoryServiceList.size() == ProductCategory.values().length);
	}
}